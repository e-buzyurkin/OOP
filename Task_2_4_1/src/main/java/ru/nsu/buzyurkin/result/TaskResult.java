package ru.nsu.buzyurkin.result;

import ru.nsu.buzyurkin.model.Task;

public record TaskResult(
        Task task,
        boolean found,
        boolean buildSuccess,
        TestResult testResult,
        float score
) {}
