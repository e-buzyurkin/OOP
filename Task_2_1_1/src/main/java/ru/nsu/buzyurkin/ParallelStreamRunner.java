package ru.nsu.buzyurkin;

import java.util.List;

/**
 * ParallelStreamRunner is a utility class for running a compound number check operation
 * on a list of integers using parallel streams.
 */
public class ParallelStreamRunner {

    /**
     * Checks if any number in the given list is compound using parallel streams.
     *
     * @param list The list of integers to be checked for compound numbers.
     * @return {@code true} if any compound number is found, {@code false} otherwise.
     */
    public static boolean anyCompound(List<Integer> list) {
        return list.parallelStream().anyMatch(n -> !PrimeUtil.isPrime(n));
    }
}
