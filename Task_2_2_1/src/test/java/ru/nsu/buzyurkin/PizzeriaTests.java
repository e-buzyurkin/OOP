package ru.nsu.buzyurkin;

import org.junit.jupiter.api.Test;
import ru.nsu.buzyurkin.pizzeria.Pizzeria;

public class PizzeriaTests {
    @Test
    public void test1() {
        Pizzeria.runPizzeria("./src/test/resources/testConfig1.json", 5);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test2() {
        Pizzeria.runPizzeria("./src/test/resources/testConfig2.json", 5);
    }

    @Test
    public void test3() {
        Pizzeria.runPizzeria("./src/test/resources/testConfig3.json", 10);
    }
}
