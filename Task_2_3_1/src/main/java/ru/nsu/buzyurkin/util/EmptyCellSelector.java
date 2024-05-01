package ru.nsu.buzyurkin.util;

import ru.nsu.buzyurkin.entity.Cell;
import ru.nsu.buzyurkin.entity.GameField;
import ru.nsu.buzyurkin.state.CellState;

import java.util.Random;

public class EmptyCellSelector {
    private GameField field;

    public EmptyCellSelector(GameField field) {
        this.field = field;
    }

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
