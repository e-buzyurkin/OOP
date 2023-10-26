package ru.nsu.buzyurkin;

import java.util.Comparator;

/**
 * This class provides utility methods for working with numbers,
 *          including comparison and addition operations.
 *
 * @param <T> The type of numbers on which the methods operate,
 *              must be a subclass of Number and Comparable.
 */
public class NumberMethods<T extends Number & Comparable> implements Comparator<T> {
    /**
     * Compares two numbers and returns a negative value if 'a' is less than 'b',
     * 0 if they are equal,
     * and a positive value if 'a' is greater than 'b'.
     *
     * @param a The first number to compare.
     * @param b The second number to compare.
     * @return A negative integer, zero, or a positive integer
     *          as 'a' is less than, equal to, or greater than 'b'.
     */
    public int compare(T a, T b) {
        return a.compareTo(b);
    }

    /**
     * Adds two numbers of the same or compatible types and returns the result.
     *
     * @param a The first number to add.
     * @param b The second number to add.
     * @return The result of adding 'a' and 'b', with the same type as the input numbers.
     */
    public T plus(T a, T b) {
        if (a instanceof Double || b instanceof Double) {
            return (T) Double.valueOf(a.doubleValue() + b.doubleValue());
        } else if(a instanceof Float || b instanceof Float) {
            return (T) Float.valueOf(a.floatValue() + b.floatValue());
        } else if(a instanceof Long || b instanceof Long) {
            return (T) Long.valueOf(a.longValue() + b.longValue());
        } else {
            return (T) Integer.valueOf(a.intValue() + b.intValue());
        }
    }
}