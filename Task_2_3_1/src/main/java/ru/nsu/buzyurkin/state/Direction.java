package ru.nsu.buzyurkin.state;

import ru.nsu.buzyurkin.entity.Cell;

public enum Direction {
    UP(new Cell(0, -1)),
    DOWN(new Cell(0, 1)),
    LEFT(new Cell(-1, 0)),
    RIGHT(new Cell(1, 0));

    private Cell cell;

    Direction(Cell cell) {
        this.cell = cell;
    }

    public Cell toCell() {
        return cell;
    }
}
