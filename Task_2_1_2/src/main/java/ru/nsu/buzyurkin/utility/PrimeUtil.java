package ru.nsu.buzyurkin.utility;

/**
 * Utility class for prime number related operations.
 */
public class PrimeUtil {
    /**
     * Checks if a given number is prime.
     *
     * @param x The number to be checked.
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
}
