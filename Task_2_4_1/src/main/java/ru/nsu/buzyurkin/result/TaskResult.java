package ru.nsu.buzyurkin.result;

import ru.nsu.buzyurkin.model.Task;

/**
 * The TaskResult record holds the result of evaluating a specific task.
 *
 * @param task the task that was evaluated.
 * @param found a flag indicating whether the task was found in the repository.
 * @param buildSuccess a flag indicating whether the task built successfully.
 * @param javadocSuccess a flag indicating whether the Javadoc generation was successful.
 * @param checkstyleSuccess a flag indicating whether the code passed Checkstyle validation.
 * @param testResult the result of running tests on the task.
 * @param score the score obtained for the task.
 */
public record TaskResult(
        Task task,
        boolean found,
        boolean buildSuccess,
        boolean javadocSuccess,
        boolean checkstyleSuccess,
        TestResult testResult,
        float score
) {}
