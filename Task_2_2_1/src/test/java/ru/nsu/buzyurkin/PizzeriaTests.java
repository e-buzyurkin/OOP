package ru.nsu.buzyurkin;

import org.junit.jupiter.api.Test;
import ru.nsu.buzyurkin.pizzeria.Pizzeria;

/**
 * Test class for testing the Pizzeria class.
 */
public class PizzeriaTests {
    /**
     * Test running the pizzeria simulation with test configuration 1.
     */
    @Test
    public void test1() {
        Pizzeria.runPizzeria("./src/test/resources/testConfig1.json", 5);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test running the pizzeria simulation with test configuration 2.
     */
    @Test
    public void test2() {
        Pizzeria.runPizzeria("./src/test/resources/testConfig2.json", 5);
    }

    /**
     * Test running the pizzeria simulation with test configuration 3.
     */
    @Test
    public void test3() {
        Pizzeria.runPizzeria("./src/test/resources/testConfig3.json", 10);
    }
}
