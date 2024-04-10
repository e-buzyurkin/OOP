package ru.nsu.buzyurkin.menu.snack;

import ru.nsu.buzyurkin.menu.MenuItem;

/**
 * Represents a snack item on the menu.
 */
public record Snack(SnackMenu snackMenu) implements MenuItem {}
