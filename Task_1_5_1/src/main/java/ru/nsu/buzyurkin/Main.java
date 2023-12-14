package ru.nsu.buzyurkin;

import ru.nsu.buzyurkin.customexceptions.IllegalArgumentQuantityException;
import ru.nsu.buzyurkin.customexceptions.IllegalExpressionException;

/**
 * The {@code Main} class contains the main method for
 *                  the Command Line Interface (CLI) application.
 */
public class Main {
    /**
     * CLI entry point. Reads a line from standard input, evaluates it and prints result.
     *
     * @param args arguments
     */
    public static void main(String[] args)
            throws IllegalExpressionException, IllegalArgumentQuantityException {
        String input = StdinReader.readLine();

        System.out.println(ExpressionEvaluator.evaluate(input));
    }
}
