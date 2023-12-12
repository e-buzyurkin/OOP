package ru.nsu.buzyurkin.customexceptions;

/**
 * The {@code IllegalArgumentQuantityException} class extends
 *              the {@code Exception} class and is used
 * to signal that the quantity of arguments provided for a mathematical operation is invalid.
 */
public class IllegalArgumentQuantityException extends Exception {
    public IllegalArgumentQuantityException() {
        super("Wrong quantity of arguments passed as an input");
    }
}
