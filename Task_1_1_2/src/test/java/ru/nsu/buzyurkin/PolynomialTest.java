package ru.nsu.buzyurkin;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PolynomialTest {

    /**
     * Tests the initialization of a polynomial.
     */
    @Test
    void test_init() {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
    }

    /**
     * Tests the addition operation of two polynomials.
     */
    @Test
    void test_plus() {
        Polynomial p1 = new Polynomial(new int[] {10, 5, 3, 7, 11});
        Polynomial p2 = new Polynomial(new int[] {5, 18, 5, 3, 7, 11});

        assertEquals(p1.plus(p2).toString(), "11x^5 + 18x^4 + 10x^3 + 8x^2 + 23x + 15");
    }

    /**
     * Tests the subtraction operation of two polynomials.
     */
    @Test
    void test_minus() {
        Polynomial p1 = new Polynomial(new int[] {10, -5, 3, 7, 11});
        Polynomial p2 = new Polynomial(new int[] {5, 18, 5, 3, -7, 11});

        assertEquals(p1.minus(p2).toString(), "-11x^5 + 18x^4 + 4x^3 - 2x^2 - 23x + 5");
    }

    /**
     * Tests the multiplication operation of two polynomials.
     */
    @Test
    void test_multiply() {
        Polynomial p1 = new Polynomial(new int[] {10, -5, 3, 7, 11, -100, 13, 53});
        Polynomial p2 = new Polynomial(new int[] {5, 18, 5, 3, -7, 11});

        assertEquals(p1.times(p2).toString(), p2.times(p1).toString());
        assertEquals(p1.times(p2).toString(), "583x^12 - 228x^11 - 1032x^10 + 1125x^9 + " +
                "719x^8 + 16x^7 - 1735x^6 - 113x^5 + 111x^4 + 94x^3 - 25x^2 + 155x + 50");
    }

    /**
     * Tests the differentiation of a polynomial.
     */
    @Test
    void test_differentiation() {
        Polynomial p1 = new Polynomial(new int[] {10, -5, 3, 7, 11, -10, 13, 1});

        assertEquals(p1.differentiate(10).toString(), "0");
        assertEquals(p1.differentiate(5).toString(), "2520x^2 + 9360x - 1200");
        assertEquals(p1.differentiate(0).toString(), "x^7 + 13x^6 - 10x^5 + 11x^4 + " +
                                                                "7x^3 + 3x^2 - 5x + 10");
    }

    /**
     * Tests the evaluation of a polynomial.
     */
    @Test
    void test_evaluation() {
        Polynomial p1 = new Polynomial(new int[] {10, -5, 3, 7, 11});

        assertEquals(p1.evaluate(0), 10);
        assertEquals(p1.evaluate(3), 1102);
        assertEquals(p1.evaluate(-1.5), 56.3125);
    }

    /**
     * Tests the equality comparison of polynomials.
     */
    @Test
    void test_equality() {
        Polynomial p1 = new Polynomial(new int[] {10, -5, 3});
        assertTrue(p1.equals(p1));

        Polynomial p2 = new Polynomial(new int[] {10, -5, 3, 0, 0});
        assertTrue(p1.equals(p2));
        assertTrue(p2.equals(p1));

        Polynomial p3 = new Polynomial(new int[] {0, 10, 3, 7, 11});
        assertFalse(p1.equals(p3));
        assertFalse(p3.equals(p1));

        Polynomial p4 = new Polynomial(new int[] {103, -10, 40, 0, 10, 3, 7, 11});
        assertFalse(p1.equals(p4));
        assertFalse(p4.equals(p1));
    }

    /**
     * Tests the string representation of a polynomial.
     */
    @Test
    void test_stringRepresentation() {
        Polynomial p1 = new Polynomial(new int[] {0, 0, 0, 0,});
        assertEquals(p1.toString(), "0");

        Polynomial p2 = new Polynomial(new int[] {103, -10, 40, 0, 10, 3, 7, -11});
        assertEquals(p2.toString(), "-11x^7 + 7x^6 + 3x^5 + 10x^4 + 40x^2 - 10x + 103");

        Polynomial p3 = new Polynomial(new int[] {0, 1, 1, 1, 1, 1, 1, 1});
        assertEquals(p3.toString(), "x^7 + x^6 + x^5 + x^4 + x^3 + x^2 + x");

        Polynomial p4 = new Polynomial(new int[] {1203});
        assertEquals(p4.toString(), "1203");
    }
}
