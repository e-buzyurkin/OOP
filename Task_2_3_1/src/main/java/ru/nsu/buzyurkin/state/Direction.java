package ru.nsu.buzyurkin.state;

import ru.nsu.buzyurkin.entity.Cell;

/**
 * Represents the possible directions of movement in the game.
 */
public enum Direction {
    UP(new Cell(0, -1)),
    DOWN(new Cell(0, 1)),
    LEFT(new Cell(-1, 0)),
    RIGHT(new Cell(1, 0));

    private Cell cell;

    /**
     * Constructs a Direction enum with the specified cell movement.
     *
     * @param cell the cell movement associated with the direction
     */
    Direction(Cell cell) {
        this.cell = cell;
    }

    /**
     * Gets the cell movement associated with the direction.
     *
     * @return the cell movement
     */
    public Cell toCell() {
        return cell;
    }
}
