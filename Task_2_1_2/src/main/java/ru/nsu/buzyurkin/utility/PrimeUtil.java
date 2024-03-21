package ru.nsu.buzyurkin.utility;

public class PrimeUtil {
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
