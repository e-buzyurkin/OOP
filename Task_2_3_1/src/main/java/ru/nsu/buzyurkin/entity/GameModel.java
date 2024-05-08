package ru.nsu.buzyurkin.entity;

import ru.nsu.buzyurkin.state.CellState;
import ru.nsu.buzyurkin.state.Direction;
import ru.nsu.buzyurkin.state.GameState;
import ru.nsu.buzyurkin.util.EmptyCellSelector;

/**
 * Represents the model of the Snake game, containing the game logic and state.
 */
public class GameModel {
    private static int WALLS_COUNT = 5;
    private static int APPLE_COUNT = 3;
    private static int WIN_LENGTH = 100;

    private GameField field;
    private Snake snake;
    private EmptyCellSelector cellSelector;
    private int score = 1;

    /**
     * Constructs a GameModel object with the specified number of rows and columns.
     *
     * @param rows the number of rows in the game field
     * @param cols the number of columns in the game field
     */
    public GameModel(int rows, int cols) {
        field = new GameField(cols, rows);
        snake = new Snake(cols / 2, rows / 2, field);

        cellSelector = new EmptyCellSelector(field);

        generateApples(APPLE_COUNT);
        generateWalls();
    }

    /**
     * Executes a single step of the game based on the provided direction.
     *
     * @param dir the direction in which the snake should move
     * @return the current state of the game after the step
     */
    public GameState step(Direction dir) {
        boolean ateApple = false;
        GameState state = GameState.PLAYING;
        CellState nextCell = snake.nextCell(dir);

        switch (nextCell) {
            case SNAKE_HEAD, WALL, SNAKE_TAIL -> {
                state = GameState.LOSE;
            }
            case APPLE -> {
                ateApple = true;
            }
            default -> {
            }
        }

        clearSnakeCells();
        snake.move(dir, ateApple);
        setSnake();

        if (ateApple) {
            score++;

            if (score == WIN_LENGTH) {
                state = GameState.WIN;
            } else {
                generateApples(1);
            }
        }

        return state;
    }

    /**
     * Gets the current game field.
     *
     * @return the game field
     */
    public GameField getField() {
        return field;
    }

    /**
     * Gets the current score of the game.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the win length required to win the game.
     *
     * @return the win length
     */
    public int getWinLength() {
        return WIN_LENGTH;
    }

    /**
     * Resets the game to its initial state.
     */
    public void resetGame() {
        score = 1;

        field = new GameField(field.getColsCount(), field.getRowsCount());
        snake = new Snake(field.getColsCount() / 2, field.getRowsCount() / 2, field);
        cellSelector = new EmptyCellSelector(field);

        generateApples(APPLE_COUNT);
        generateWalls();
    }

    private void clearSnakeCells() {
        for (int i = 0; i < field.getRowsCount(); i++) {
            for (int j = 0; j < field.getColsCount(); j++) {

                if (field.getCell(j, i) == CellState.SNAKE_HEAD
                    || field.getCell(j, i) == CellState.SNAKE_TAIL) {

                    field.setCell(CellState.EMPTY, j, i);
                }

            }
        }
    }

    private void setSnake() {
        field.setCell(CellState.SNAKE_HEAD, snake.getHead());

        for (Cell cell : snake.getTail()) {
            field.setCell(CellState.SNAKE_TAIL, cell);
        }
    }

    private void generateApples(int count) {
        for (int i = 0; i < count; i++) {
            field.setCell(CellState.APPLE, cellSelector.getEmptyCell());
        }
    }

    private void generateWalls() {
        for (int i = 0; i < WALLS_COUNT; i++) {
            field.setCell(CellState.WALL, cellSelector.getEmptyCell());
        }
    }
}
