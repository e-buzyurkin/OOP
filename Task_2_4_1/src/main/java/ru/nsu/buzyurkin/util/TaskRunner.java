package ru.nsu.buzyurkin.util;

import java.io.File;
import java.util.*;

import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import lombok.Getter;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import org.gradle.tooling.BuildException;
import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.TestExecutionException;
import org.gradle.tooling.events.*;
import org.gradle.tooling.events.test.*;
import ru.nsu.buzyurkin.git.Repository;
import ru.nsu.buzyurkin.model.Assignment;
import ru.nsu.buzyurkin.model.Config;
import ru.nsu.buzyurkin.model.Student;
import ru.nsu.buzyurkin.model.Task;
import ru.nsu.buzyurkin.result.StudentResult;
import ru.nsu.buzyurkin.result.TaskResult;
import ru.nsu.buzyurkin.result.TestResult;


public class TaskRunner {
    private Map<String, Task> tasks;
    private Map<String, List<Assignment>> assignments;
    private Repository repo;

    public TaskRunner(Config config, Repository repo) {
        this.repo = repo;
        tasks = new HashMap<>();
        for (Task task : config.getTasks()) {
            tasks.put(task.getId(), task);
        }
        assignments = new LinkedHashMap<>();
        for (Assignment assignment : config.getAssignments()) {
            assignments.computeIfAbsent(assignment.getStudentId(), k -> new ArrayList<>()).add(assignment);
        }
    }

    public StudentResult evaluateStudent(Student student, File repoDirectory) {
        System.out.printf("%nEvaluating %s%n", student.getName());
        List<TaskResult> taskResults = new ArrayList<>();
        float totalScore = 0;
        for (Assignment assignment : assignments.get(student.getId())) {
            if (assignment.getStudentId() == student.getId()) {
                Task task = tasks.get(assignment.getTaskId());
                File taskDirectory = new File(repoDirectory, task.getId());
                TaskResult result = evaluateTask(task, taskDirectory, assignment.getBranch());
                taskResults.add(result);
                totalScore += result.score();
            }
        }
        return new StudentResult(student, true, taskResults, totalScore);
    }

    private TaskResult evaluateTask(Task task, File taskDirectory, String branch) {
        boolean couldCheckout = repo.checkoutBranch(branch);
        if (!couldCheckout || !taskDirectory.exists()) {
            System.out.printf("Could not find %s%n", task.getName());
            return new TaskResult(task, false,
                    false,
                    false,
                    false,
                    null,
                    0);
        }

        ProjectConnection connection = GradleConnector
                .newConnector()
                .useGradleVersion("8.4")
                .forProjectDirectory(taskDirectory)
                .connect();

        float achievedScore = 0;
        boolean buildSuccessful = false;
        boolean javadocSuccess = false;
        boolean checkstyleSuccess = false;
        TestResult testResult = null;

        buildSuccessful = buildTask(connection);
        if (!buildSuccessful) {
            connection.close();
            return new TaskResult(task, true,
                    false,
                    false,
                    false,
                    null,
                    0);
        }
        achievedScore += task.getScore();

        if (task.isRunTests()) {
            testResult = testTask(connection, task.getId());
        }

        javadocSuccess = createJavadoc(connection);

        try {
            checkstyleSuccess = runCheckstyle(allFilesList(taskDirectory)) > 0;
        } catch (CheckstyleException e) {
            throw new RuntimeException(e);
        }

        connection.close();

        return new TaskResult(task, true,
                buildSuccessful,
                javadocSuccess,
                checkstyleSuccess,
                testResult,
                achievedScore
        );
    }

    private boolean buildTask(ProjectConnection connection) {
        BuildLauncher build = connection.newBuild().forTasks("assemble");
        try {
            build.run();
            return true;
        } catch (BuildException e) {
            return false;
        }
    }

    private int runCheckstyle(List<File> files) throws CheckstyleException {
        File configFile = new File("../.github/google_checks.xml");
        Configuration configuration =
                ConfigurationLoader.loadConfiguration(
                        configFile.getAbsolutePath(),
                        new PropertiesExpander(new Properties()),
                        ConfigurationLoader.IgnoredModulesOptions.OMIT);

        AuditListenerImpl listener = new AuditListenerImpl();

        Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(configuration);
        checker.addListener(listener);
        checker.process(files);
        checker.destroy();
        return listener.getErrorCount();
    }

    private boolean createJavadoc(ProjectConnection connection) {
        BuildLauncher javadoc = connection.newBuild().forTasks("javadoc");
        try {
            javadoc.run();
            return true;
        } catch (BuildException e) {
            return false;
        }
    }

    private TestResult testTask(ProjectConnection connection, String taskId) {
        TestProgressListener listener = new TestProgressListener();
        BuildLauncher test = connection
                .newBuild()
                .forTasks("test")
                .withArguments("--rerun-tasks")
                .addProgressListener(listener, OperationType.TEST);

        try {
            test.run();
            return listener.getResult();
        } catch (TestExecutionException e) {
            return listener.getResult();
        } catch (BuildException e) {
            return new TestResult(false, 0, 0, 0);
        }
    }

    private List<File> allFilesList(File dir) {
        List<File> list = new ArrayList<>();
        for (final File fileEntry : dir.listFiles()) {
            if (fileEntry.isDirectory()) {
                list.addAll(allFilesList(fileEntry));
            } else {
                list.add(fileEntry);
            }
        }

        return list;
    }

    private class AuditListenerImpl implements AuditListener {
        private @Getter int errorCount;
        private @Getter int exceptionCount;

        public AuditListenerImpl() {
            errorCount = 0;
            exceptionCount = 0;
        }

        @Override
        public void auditStarted(AuditEvent auditEvent) {
        }

        @Override
        public void auditFinished(AuditEvent auditEvent) {
        }

        @Override
        public void fileStarted(AuditEvent auditEvent) {
        }

        @Override
        public void fileFinished(AuditEvent auditEvent) {
        }

        @Override
        public void addError(AuditEvent event) {
            errorCount++;
        }

        @Override
        public void addException(AuditEvent event, Throwable throwable) {
            exceptionCount++;
        }
    }

    private class TestProgressListener implements ProgressListener {
        private @Getter int testsPassed = 0;
        private @Getter int testsFailed = 0;
        private @Getter int testsSkipped = 0;

        public void statusChanged(ProgressEvent event) {
            OperationDescriptor descriptor = event.getDescriptor();
            if (event instanceof TestFinishEvent && descriptor instanceof JvmTestOperationDescriptor) {
                if (((JvmTestOperationDescriptor) descriptor).getJvmTestKind() == JvmTestKind.ATOMIC) {
                    TestOperationResult result = ((TestFinishEvent) event).getResult();
                    if (result instanceof TestSuccessResult) {
                        testsPassed++;
                    }
                    if (result instanceof TestFailureResult) {
                        testsFailed++;
                    }
                    if (result instanceof TestSkippedResult) {
                        testsSkipped++;
                    }
                }
            }
        }

        private TestResult getResult() {
            return new TestResult(true, testsPassed, testsFailed, testsSkipped);
        }
    }
}
