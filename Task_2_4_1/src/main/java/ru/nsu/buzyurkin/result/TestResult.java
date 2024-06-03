package ru.nsu.buzyurkin.result;

/**
 * The TestResult record holds the result of running tests.
 *
 * @param buildSuccess a flag indicating whether the build was successful before running tests.
 * @param testsPassed the number of tests that passed.
 * @param testsFailed the number of tests that failed.
 * @param testsSkipped the number of tests that were skipped.
 */
public record TestResult(
        boolean buildSuccess,
        int testsPassed,
        int testsFailed,
        int testsSkipped
) {}
