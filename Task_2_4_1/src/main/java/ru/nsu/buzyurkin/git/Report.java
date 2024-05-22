package ru.nsu.buzyurkin.git;

import ru.nsu.buzyurkin.util.ReportWriter;
import ru.nsu.buzyurkin.util.TaskRunner;
import ru.nsu.buzyurkin.model.Config;
import ru.nsu.buzyurkin.model.Student;
import ru.nsu.buzyurkin.result.StudentResult;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Report {
    private List<StudentResult> results;

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

    public void printReport() {
        ReportWriter writer = new ReportWriter(results);
        writer.printReport();
    }
}
