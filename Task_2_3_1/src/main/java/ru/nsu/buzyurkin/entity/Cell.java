package ru.nsu.buzyurkin.entity;

/**
 * Represents a cell in the game field.
 */
public class Cell {
    private int x;
    private int y;

    /**
     * Constructs a Cell object with the specified coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a Cell object as a copy of another cell.
     *
     * @param cell the cell to copy
     */
    public Cell(Cell cell) {
        this.x = cell.x;
        this.y = cell.y;
    }

    /**
     * Gets the x-coordinate of the cell.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the cell.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the coordinates of the cell.
     *
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     */
    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the coordinates of the cell as a copy of another cell.
     *
     * @param cell the cell to copy coordinates from
     */
    public void set(Cell cell) {
        this.x = cell.x;
        this.y = cell.y;
    }

    /**
     * Adds the coordinates of another cell to this cell.
     *
     * @param cell the cell whose coordinates to add
     */
    public void add(Cell cell) {
        this.x += cell.x;
        this.y += cell.y;
    }

    /**
     * Fixes the cell coordinates to stay within the specified boundaries.
     *
     * @param x the maximum x-coordinate
     * @param y the maximum y-coordinate
     */
    public void fixBorders(int x, int y) {
        add(new Cell(x, y));

        this.x %= x;
        this.y %= y;
    }
}
