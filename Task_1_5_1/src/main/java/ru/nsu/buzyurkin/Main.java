package ru.nsu.buzyurkin;

import ru.nsu.buzyurkin.customExceptions.IllegalArgumentQuantityException;
import ru.nsu.buzyurkin.customExceptions.IllegalExpressionException;

public class Main {
    /**
     * CLI entry point. Reads a line from standard input, evaluates it and prints result.
     *
     * @param args arguments
     */
    public static void main(String args[]) throws IllegalExpressionException, IllegalArgumentQuantityException {
        String input = StdinReader.readLine();

        System.out.println(ExpressionEvaluator.evaluate(input));
    }
}
