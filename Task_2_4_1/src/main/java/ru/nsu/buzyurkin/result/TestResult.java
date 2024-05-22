package ru.nsu.buzyurkin.result;

public record TestResult(
        boolean buildSuccess,
        int testsPassed,
        int testsFailed,
        int testsSkipped
) {}
