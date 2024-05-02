package ru.nsu.buzyurkin.entity;


import java.util.ArrayList;
import java.util.List;
import ru.nsu.buzyurkin.state.CellState;

/**
 * Represents the game field.
 */
public class GameField {
    private int rows;
    private int cols;

    private List<List<CellState>> field = new ArrayList<>();

    /**
     * Constructs a GameField object with the specified number of rows and columns.
     *
     * @param rows the number of rows
     * @param cols the number of columns
     */
    public GameField(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        for (int i = 0; i < rows; i++) {
            field.add(new ArrayList<>());
            for (int j = 0; j < cols; j++) {
                field.get(field.size() - 1).add(CellState.EMPTY);
            }
        }
    }

    /**
     * Sets the state of the cell at the specified coordinates.
     *
     * @param state the state to set
     * @param cell  the cell coordinates
     */
    public void setCell(CellState state, Cell cell) {
        field.get(cell.getY()).set(cell.getX(), state);
    }

    /**
     * Sets the state of the cell at the specified coordinates.
     *
     * @param state the state to set
     * @param x     the x-coordinate of the cell
     * @param y     the y-coordinate of the cell
     */
    public void setCell(CellState state, int x, int y) {
        field.get(y).set(x, state);
    }

    /**
     * Gets the state of the cell at the specified coordinates.
     *
     * @param cell the cell coordinates
     * @return the state of the cell
     */
    public CellState getCell(Cell cell) {
        return field.get(cell.getY()).get(cell.getX());
    }

    /**
     * Gets the state of the cell at the specified coordinates.
     *
     * @param x the x-coordinate of the cell
     * @param y the y-coordinate of the cell
     * @return the state of the cell
     */
    public CellState getCell(int x, int y) {
        return field.get(y).get(x);
    }

    /**
     * Gets the number of rows in the game field.
     *
     * @return the number of rows
     */
    public int getRowsCount() {
        return rows;
    }

    /**
     * Gets the number of columns in the game field.
     *
     * @return the number of columns
     */
    public int getColsCount() {
        return cols;
    }
}
