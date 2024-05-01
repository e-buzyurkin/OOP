package ru.nsu.buzyurkin.util;

import java.util.Random;

/**
 * Utility class for generating random enum values.
 */
public class RandomEnum {
    private RandomEnum() {
    }

    private static Random RANDOM = new Random();

    /**
     * Generates a random enum value of the specified enum class.
     *
     * @param enumClass The class of the enum.
     * @param <E>       The type of the enum.
     * @return A random enum value.
     */
    public static <E> E randomEnum(Class<E> enumClass) {
        E[] values = enumClass.getEnumConstants();
        return values[RANDOM.nextInt(values.length)];
    }
}
