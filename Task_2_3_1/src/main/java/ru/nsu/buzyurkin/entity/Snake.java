package ru.nsu.buzyurkin.entity;


import java.util.ArrayDeque;
import java.util.Queue;
import ru.nsu.buzyurkin.state.CellState;
import ru.nsu.buzyurkin.state.Direction;

/**
 * Represents the snake entity in the game.
 */
public class Snake {
    private GameField field;

    private Cell headCell;
    private Queue<Cell> tailCells = new ArrayDeque<>();

    /**
     * Constructs a Snake object with the specified coordinates and game field.
     *
     * @param x     the initial x-coordinate of the snake's head
     * @param y     the initial y-coordinate of the snake's head
     * @param field the game field
     */
    public Snake(int x, int y, GameField field) {
        headCell = new Cell(x, y);
        this.field = field;
    }

    /**
     * Moves the snake in the specified direction.
     *
     * @param dir      the direction to move the snake
     * @param ateApple true if the snake ate an apple, false otherwise
     */
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

    /**
     * Calculates the position of the new head cell
     *          after moving the snake in the specified direction.
     *
     * @param dir the direction to move the snake
     * @return the new head cell position
     */
    public Cell newHeadCell(Direction dir) {
        Cell newHead = new Cell(headCell);
        newHead.add(dir.toCell());

        return newHead;
    }

    /**
     * Determines the state of the next cell after moving the snake in the specified direction.
     *
     * @param dir the direction to move the snake
     * @return the state of the next cell
     */
    public CellState nextCell(Direction dir) {
        Cell newHead = newHeadCell(dir);
        newHead.fixBorders(field.getColsCount(), field.getRowsCount());

        return field.getCell(newHead);
    }

    /**
     * Gets the current position of the snake's head.
     *
     * @return the position of the snake's head
     */
    public Cell getHead() {
        return headCell;
    }

    /**
     * Gets the current positions of the snake's tail segments.
     *
     * @return the positions of the snake's tail segments
     */
    public Queue<Cell> getTail() {
        return tailCells;
    }
}
