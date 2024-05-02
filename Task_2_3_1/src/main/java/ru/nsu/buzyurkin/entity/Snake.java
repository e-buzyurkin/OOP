package ru.nsu.buzyurkin.entity;


import java.util.ArrayDeque;
import java.util.Queue;
import ru.nsu.buzyurkin.state.Direction;
import ru.nsu.buzyurkin.state.CellState;

public class Snake {
    private GameField field;

    private Cell headCell;
    private Queue<Cell> tailCells = new ArrayDeque<>();

    public Snake(int x, int y, GameField field) {
        headCell = new Cell(x, y);
        this.field = field;
    }

    public void move(Direction dir, boolean ateApple) {
        if (tailCells.size() > 0) {
            if (!ateApple) {
                tailCells.poll();
            }

            tailCells.add(new Cell(headCell));
        } else if (ateApple) {
            tailCells.add(new Cell(headCell));
        }

        headCell.add(dir.toCell());
        headCell.fixBorders(field.getColsCount(), field.getRowsCount());
    }

    public Cell newHeadCell(Direction dir) {
        Cell newHead = new Cell(headCell);
        newHead.add(dir.toCell());

        return newHead;
    }

    public CellState nextCell(Direction dir) {
        Cell newHead = newHeadCell(dir);
        newHead.fixBorders(field.getColsCount(), field.getRowsCount());

        return field.getCell(newHead);
    }

    public Cell getHead() {
        return headCell;
    }

    public Queue<Cell> getTail() {
        return tailCells;
    }
}
