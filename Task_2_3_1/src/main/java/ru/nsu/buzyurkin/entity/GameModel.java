package ru.nsu.buzyurkin.entity;

import ru.nsu.buzyurkin.state.CellState;
import ru.nsu.buzyurkin.state.Direction;
import ru.nsu.buzyurkin.state.GameState;
import ru.nsu.buzyurkin.util.EmptyCellSelector;

public class GameModel {
    private static int WALLS_COUNT = 5;
    private static int APPLE_COUNT = 3;
    private static int WIN_LENGTH = 100;

    private GameField field;
    private Snake snake;
    private EmptyCellSelector cellSelector;
    private int score = 1;

    public GameModel(int rows, int cols) {
        field = new GameField(cols, rows);
        snake = new Snake(cols / 2, rows / 2, field);

        cellSelector = new EmptyCellSelector(field);

        generateApples(APPLE_COUNT);
        generateWalls();
    }

    public GameState step(Direction dir) {
        boolean ateApple = false;
        GameState state = GameState.PLAYING;
        CellState nextCell = snake.nextCell(dir);

        switch (nextCell) {
            case SNAKE_HEAD -> {
                state = GameState.LOSE;
            }
            case APPLE -> {
                ateApple = true;
            }
            default -> {
                break;
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

    public GameField getField() {
        return field;
    }

    public int getScore() {
        return score;
    }

    public int getWinLength() {
        return WIN_LENGTH;
    }

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
