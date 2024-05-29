package ru.nsu.buzyurkin.result;

import ru.nsu.buzyurkin.model.Task;

public record TaskResult(
        Task task,
        boolean found,
        boolean buildSuccess,
        boolean javadocSuccess,
        boolean checkstyleSuccess,
        TestResult testResult,
        float score
) {}
