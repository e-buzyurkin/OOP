package ru.nsu.buzyurkin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import ru.nsu.buzyurkin.operations.*;

/**
 * The {@code OperationFactory} class provides a mechanism for creating instances of mathematical
 * operations based on operator symbols.
 */
public class OperationFactory {
    private Map<String, Operation> operationSet;

    /**
     * Constructs an {@code OperationFactory} and initializes the operationSet map with default
     * instances of mathematical operations.
     */
    public OperationFactory() {
        operationSet = new HashMap<>();

        operationSet.put("+", new Addition());
        operationSet.put("-", new Subtraction());
        operationSet.put("*", new Multiplication());
        operationSet.put("/", new Division());
        operationSet.put("sin", new Sin());
        operationSet.put("cos", new Cos());
        operationSet.put("log", new Logarithm());
        operationSet.put("sqrt", new Sqrt());
        operationSet.put("pow", new Power());
    }

    /**
     * Retrieves an optional instance of an operation based on the provided operator symbol.
     *
     * @param operator The operator symbol for which an operation instance is requested.
     * @return An optional containing the corresponding operation instance, or an empty optional
     * if the operator is not supported.
     */
    public Optional<Operation> getOperation(String operator) {
        return Optional.ofNullable(operationSet.get(operator));
    }
}
