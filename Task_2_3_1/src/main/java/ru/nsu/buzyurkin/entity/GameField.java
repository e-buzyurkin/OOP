package ru.nsu.buzyurkin.entity;

import ru.nsu.buzyurkin.state.CellState;

import java.util.ArrayList;
import java.util.List;

public class GameField {
    private int rows;
    private int cols;

    private List<List<CellState>> field = new ArrayList<>();

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

    public void setCell(CellState state, Cell cell) {
        field.get(cell.getY()).set(cell.getX(), state);
    }

    public void setCell(CellState state, int x, int y) {
        field.get(y).set(x, state);
    }

    public CellState getCell(Cell cell) {
        return field.get(cell.getY()).get(cell.getX());
    }

    public CellState getCell(int x, int y) {
        return field.get(y).get(x);
    }

    public int getRowsCount() {
        return rows;
    }

    public int getColsCount() {
        return cols;
    }
}
