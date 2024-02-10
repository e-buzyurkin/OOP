package ru.nsu.buzyurkin;

import java.util.List;

/**
 * SequentialRunner is a utility class for running a compound number check operation
 * on a list of integers sequentially using the PrimeUtil class.
 */
public class SequentialRunner {
    /**
     * Checks if any number in the given list is not a prime number using sequential processing.
     *
     * @param list The list of integers to be checked for non-prime numbers.
     * @return {@code true} if any non-prime number is found, {@code false} otherwise.
     */
    public static boolean anyCompound(List<Integer> list) {
        return PrimeUtil.anyCompound(list);
    }
}
