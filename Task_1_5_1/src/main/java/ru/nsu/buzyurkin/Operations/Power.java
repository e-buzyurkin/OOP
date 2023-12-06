package ru.nsu.buzyurkin.Operations;

import ru.nsu.buzyurkin.Operation;

import java.util.List;

/**
 * Raises first number to the second number's power.
 */
public class Power implements Operation {
    private int argumentsCount = 2;

    @Override
    public int getArgumentCount() {
        return argumentsCount;
    }

    @Override
    public String compute(List<String> arguments) {
        if (arguments.size() != argumentsCount) {
            throw new IllegalArgumentException();
        }

        double x = Double.parseDouble(arguments.get(0));
        double y = Double.parseDouble(arguments.get(1));

        return String.valueOf(Math.pow(x, y));
    }
}
