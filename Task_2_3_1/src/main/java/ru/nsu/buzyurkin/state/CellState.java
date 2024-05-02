package ru.nsu.buzyurkin.state;

/**
 * Represents the possible states of a cell in the game field.
 */
public enum CellState {
    EMPTY,
    SNAKE_HEAD,
    SNAKE_TAIL,
    APPLE,
    WALL
}
