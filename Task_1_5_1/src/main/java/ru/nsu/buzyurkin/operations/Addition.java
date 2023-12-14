package ru.nsu.buzyurkin.operations;

import java.util.List;
import ru.nsu.buzyurkin.Operation;

/**
 * Adds two numbers together.
 */
public class Addition implements Operation {
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

        return String.valueOf(x + y);
    }
}
