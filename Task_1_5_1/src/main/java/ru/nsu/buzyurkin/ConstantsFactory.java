package ru.nsu.buzyurkin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The {@code ConstantsFactory} class facilitates
 *              the retrieval of predefined mathematical constants
 * by their symbolic representation.
 */
public class ConstantsFactory {
    private Map<String, Double> constantsSet;

    /**
     * Constructs a {@code ConstantsFactory} and initializes the constantsSet map with commonly
     * used mathematical constants.
     */
    public ConstantsFactory() {
        constantsSet = new HashMap<>();

        constantsSet.put("e", Math.E);
        constantsSet.put("pi", Math.PI);
    }

    /**
     * Retrieves an optional string representation of a predefined constant based on its symbolic
     * representation.
     *
     * @param symbol The symbolic representation of the constant.
     * @return An optional containing the string representation
     *          of the constant, or an empty optional
     *              if the constant is not found.
     */
    public Optional<String> getConstant(String symbol) {
        Double constant = constantsSet.get(symbol);
        if (constant != null) {
            return Optional.of(constant.toString());
        } else {
            return Optional.empty();
        }
    }
}
