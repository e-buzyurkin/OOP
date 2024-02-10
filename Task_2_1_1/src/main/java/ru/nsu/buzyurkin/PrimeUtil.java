package ru.nsu.buzyurkin;

import java.util.List;

/**
 * PrimeUtil is a utility class containing methods for prime number-related operations.
 */
public class PrimeUtil {

    /**
     * Checks if the given integer is a prime number.
     *
     * @param x The integer to be checked for primality.
     * @return {@code true} if the number is prime, {@code false} otherwise.
     */
    public static boolean isPrime(int x) {
        if (x == 0 || x == 1) {
            return false;
        }

        for (int i = 2; i < Math.sqrt(x) + 1; i++) {
            if (x % i == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if any number in the given list is not a prime number.
     *
     * @param list The list of integers to be checked for non-prime numbers.
     * @return {@code true} if any non-prime number is found, {@code false} otherwise.
     */
    public static boolean anyCompound(List<Integer> list) {
        return list.stream().anyMatch(x -> !isPrime(x));
    }
}
