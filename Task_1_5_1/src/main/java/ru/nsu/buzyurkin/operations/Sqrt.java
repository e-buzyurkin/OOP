package ru.nsu.buzyurkin.operations;

import java.util.List;
import ru.nsu.buzyurkin.Operation;

/**
 * Returns a square root of given value.
 */
public class Sqrt implements Operation {
    private int argumentsCount = 1;

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

        return String.valueOf(Math.sqrt(x));
    }
}
