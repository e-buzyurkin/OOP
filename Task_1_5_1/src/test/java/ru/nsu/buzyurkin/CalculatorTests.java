package ru.nsu.buzyurkin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.buzyurkin.customExceptions.IllegalArgumentQuantityException;
import ru.nsu.buzyurkin.customExceptions.IllegalExpressionException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for expression evaluator.
 */
public class CalculatorTests {
    @Test
    void test_basic() throws IllegalExpressionException, IllegalArgumentQuantityException {
        String expr = "sin + - 1 2 1";
        assertEquals(Double.valueOf(0),
                Double.parseDouble(ExpressionEvaluator.evaluate(expr)));
    }

    @Test
    void test_log() throws IllegalExpressionException, IllegalArgumentQuantityException {
        String expr = "log 10";
        assertEquals(Double.valueOf(Math.log(10)),
                Double.parseDouble(ExpressionEvaluator.evaluate(expr)));
    }

    @Test
    void test_order() throws IllegalExpressionException, IllegalArgumentQuantityException {
        String expr = "+ * 2 2 2";
        assertEquals(Double.valueOf(6),
                Double.parseDouble(ExpressionEvaluator.evaluate(expr)));
    }

    @Test
    void test_euler() throws IllegalExpressionException, IllegalArgumentQuantityException {
        String expr = "log e";
        assertEquals(Double.valueOf(1),
                Double.parseDouble(ExpressionEvaluator.evaluate(expr)));
    }

    @Test
    void test_pi() throws IllegalExpressionException, IllegalArgumentQuantityException {
        String expr = "/ * pi pi pi";
        assertEquals(Double.valueOf(Math.PI),
                Double.parseDouble(ExpressionEvaluator.evaluate(expr)));
    }

    @Test
    void test_long() throws IllegalExpressionException, IllegalArgumentQuantityException {
        String expr = "sqrt + pow pi 2 pow e 3";
        assertEquals(Double.valueOf(Math.sqrt(Math.pow(Math.PI, 2) + Math.pow(Math.E, 3))),
                Double.parseDouble(ExpressionEvaluator.evaluate(expr)));
    }

    @Test
    void test_argumentQuantity() {
        String exprTooMany = "sin + - 1 2 3 4";
        String exprNotEnough = "sin + - 1 2";

        Assertions.assertThrows(
                IllegalArgumentQuantityException.class,
                () -> ExpressionEvaluator.evaluate(exprTooMany)
        );

        Assertions.assertThrows(
                IllegalArgumentQuantityException.class,
                () -> ExpressionEvaluator.evaluate(exprNotEnough)
        );
    }

    @Test
    void test_wrongArguments() {
        String expr1 = "log2 pi";
        String expr2 = "plus 1 2";
        String expr3 = "+ piw euler";

        Assertions.assertThrows(
                IllegalExpressionException.class,
                () -> ExpressionEvaluator.evaluate(expr1)
        );

        Assertions.assertThrows(
                IllegalExpressionException.class,
                () -> ExpressionEvaluator.evaluate(expr2)
        );

        Assertions.assertThrows(
                IllegalExpressionException.class,
                () -> ExpressionEvaluator.evaluate(expr3)
        );
    }
}
