package ru.nsu.buzyurkin.util;



import java.io.File;
import java.util.*;

import lombok.Getter;



import org.gradle.tooling.BuildException;
import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.TestExecutionException;
import org.gradle.tooling.events.OperationDescriptor;
import org.gradle.tooling.events.OperationType;
import org.gradle.tooling.events.ProgressEvent;
import org.gradle.tooling.events.ProgressListener;
import org.gradle.tooling.events.test.JvmTestKind;
import org.gradle.tooling.events.test.JvmTestOperationDescriptor;
import org.gradle.tooling.events.test.TestFailureResult;
import org.gradle.tooling.events.test.TestFinishEvent;
import org.gradle.tooling.events.test.TestOperationResult;
import org.gradle.tooling.events.test.TestSkippedResult;
import org.gradle.tooling.events.test.TestSuccessResult;
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
            return new TaskResult(task, false, false, null, 0);
        }

        ProjectConnection connection = GradleConnector
                .newConnector()
                .useGradleVersion("8.4")
                .forProjectDirectory(taskDirectory)
                .connect();

        float achievedScore = 0;
        boolean buildSuccessful = false;
        TestResult testResult = null;

        System.out.printf("Building %s: ", task.getName());
        buildSuccessful = buildTask(connection);
        if (buildSuccessful) {
            System.out.printf("success%n");
            achievedScore += task.getScore();
            if (task.isRunTests()) {
                System.out.printf("Testing %s: ", task.getName());
                testResult = testTask(connection, task.getId());
                if (testResult.buildSuccess() && testResult.testsFailed() == 0) {
                    System.out.printf("success%n");
                } else {
                    System.out.printf("failure%n");
                    achievedScore /= 2;
                }
            }
        } else {
            System.out.printf("failure%n");
        }
        connection.close();

        return new TaskResult(task, true, buildSuccessful, testResult, achievedScore);
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
