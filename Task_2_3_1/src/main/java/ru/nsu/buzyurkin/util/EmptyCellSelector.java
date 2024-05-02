package ru.nsu.buzyurkin.util;

import java.util.Random;
import ru.nsu.buzyurkin.entity.Cell;
import ru.nsu.buzyurkin.entity.GameField;
import ru.nsu.buzyurkin.state.CellState;

/**
 * Helper class for selecting an empty cell on the game field.
 */
public class EmptyCellSelector {
    private GameField field;

    /**
     * Constructs an EmptyCellSelector object with the specified game field.
     *
     * @param field the game field
     */
    public EmptyCellSelector(GameField field) {
        this.field = field;
    }

    /**
     * Selects an empty cell on the game field.
     *
     * @return the empty cell selected
     */
    public Cell getEmptyCell() {
        Random random = new Random();

        while (true) {
            int x = Math.abs(random.nextInt()) % field.getColsCount();
            int y = Math.abs(random.nextInt()) % field.getRowsCount();

            if (field.getCell(x, y) == CellState.EMPTY) {
                return new Cell(x, y);
            }
        }
    }
}
