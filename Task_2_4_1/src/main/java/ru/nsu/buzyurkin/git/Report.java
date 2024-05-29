package ru.nsu.buzyurkin.git;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ru.nsu.buzyurkin.util.ReportWriter;
import ru.nsu.buzyurkin.util.TaskRunner;
import ru.nsu.buzyurkin.model.Config;
import ru.nsu.buzyurkin.model.Student;
import ru.nsu.buzyurkin.result.StudentResult;


/**
 * The Report class generates a report based on student repositories.
 * It clones the repositories, runs specified tasks, and collects results.
 */
public class Report {
    private List<StudentResult> results;

    /**
     * Constructs a Report instance by cloning student repositories and evaluating them.
     *
     * @param config the configuration object containing the necessary setup.
     */
    public Report(Config config) {
        Repository repo = new Repository(config);
        TaskRunner runner = new TaskRunner(config, repo);
        results = new ArrayList<>();

        for (Student student : config.getStudents()) {
            StudentResult studentResult;
            File repoDir = repo.cloneRepo(student);
            if (repoDir != null) {
                studentResult = runner.evaluateStudent(student, repoDir);
            } else {
                studentResult = new StudentResult(student, false, Collections.emptyList(), 0);
            }

            results.add(studentResult);
        }
    }

    /**
     * Prints the generated report using the ReportWriter.
     */
    public void printReport() {
        ReportWriter writer = new ReportWriter(results);
        writer.printReport();
    }
}
