package ru.nsu.buzyurkin;

import ru.nsu.buzyurkin.CustomExceptions.IllegalArgumentQuantityException;
import ru.nsu.buzyurkin.CustomExceptions.IllegalExpressionException;

import java.util.*;

/**
 * The {@code ExpressionEvaluator} class evaluates mathematical expressions written in a postfix notation.
 */
public class ExpressionEvaluator {
    private static OperationFactory operationFactory = new OperationFactory();
    private static ConstantsFactory constantsFactory = new ConstantsFactory();

    /**
     * Evaluates a mathematical expression in postfix notation.
     *
     * @param expression The mathematical expression to be evaluated.
     * @return The result of the evaluation as a string.
     * @throws IllegalExpressionException    If the expression is not a valid mathematical expression.
     * @throws IllegalArgumentQuantityException If the number of arguments for an operation is invalid.
     */
    public static String evaluate(String expression)
            throws IllegalExpressionException, IllegalArgumentQuantityException {
        List<String> tokens = tokenize(expression);

        Collections.reverse(tokens);

        Stack<String> stack = new Stack<>();

        for (String token : tokens) {
            TokenType tokenType = getTokenType(token);

            switch (tokenType) {
                case NUMBER:
                    stack.push(String.valueOf(Double.parseDouble(token)));
                    break;
                case OPERATION:
                    Optional<Operation> operation = operationFactory.getOperation(token);
                    if (stack.size() < operation.get().getArgumentCount()) {
                        throw new IllegalArgumentQuantityException();
                    }

                    List<String> arguments = new ArrayList<>();
                    for (int i = 0; i < operation.get().getArgumentCount(); i++) {
                        arguments.add(stack.pop());
                    }

                    stack.push(operation.get().compute(arguments));
                    break;
                case CONSTANT:
                    stack.push(constantsFactory.getConstant(token).get());
                    break;
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentQuantityException();
        }

        return stack.pop();
    }


    private enum TokenType {
        NUMBER,
        OPERATION,
        CONSTANT
    }

    /**
     * Determines the type of a token in the expression.
     *
     * @param token The token to be classified.
     * @return The type of the token (NUMBER, OPERATION, or CONSTANT).
     * @throws IllegalExpressionException If the token is not a valid part of a mathematical expression.
     */
    private static TokenType getTokenType(String token) throws IllegalExpressionException {
        Optional<Operation> operation = operationFactory.getOperation(token);
        if (operation.isPresent()) {
            return TokenType.OPERATION;
        }

        Optional<String> constant = constantsFactory.getConstant(token);
        if (constant.isPresent()) {
            return TokenType.CONSTANT;
        }

        try {
            double number = Double.parseDouble(token);
        } catch (NumberFormatException e) {
            throw new IllegalExpressionException();
        }

        return TokenType.NUMBER;
    }

    /**
     * Tokenizes a string representing a mathematical expression.
     *
     * @param str The string expression to be tokenized.
     * @return A list of tokens obtained by splitting the expression.
     */
    private static List<String> tokenize(String str) {
        return new ArrayList<>(List.of(str.split(" ")));
    }
}
