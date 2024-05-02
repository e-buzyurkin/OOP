package ru.nsu.buzyurkin;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ru.nsu.buzyurkin.entity.GameField;

/**
 * Tests for snake.
 */
public class SnakeTest {
    /**
     * Tesing GameField initiation.
     */
    @Test
    void testField() {
        GameField field = new GameField(16, 16);

        assertEquals(16, field.getRowsCount());
        assertEquals(16, field.getColsCount());
    }
}
