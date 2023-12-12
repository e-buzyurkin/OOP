package ru.nsu.buzyurkin.customexceptions;

/**
 * The {@code IllegalExpressionException} class extends
 *              the {@code Exception} class and is used
 * to signal that a mathematical expression is not valid or well-formed.
 */
public class IllegalExpressionException extends Exception {
    public IllegalExpressionException() {
        super("Wrong expression passes as an input");
    }


}
