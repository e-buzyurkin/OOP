package ru.nsu.buzyurkin;

import java.util.List;

/**
 * The {@code Operation} interface defines methods
 *      that must be implemented by classes representing
 * various mathematical operations.
 */
public interface Operation {
    /**
     * Gets the number of arguments required for this operation.
     *
     * @return The number of arguments required for the operation.
     */
    int getArgumentCount();

    /**
     * Computes the result of the operation using the provided list of string arguments.
     *
     * @param arguments A list of string arguments on which the operation is to be performed.
     * @return The result of the operation as a string.
     */
    String compute(List<String> arguments);
}
