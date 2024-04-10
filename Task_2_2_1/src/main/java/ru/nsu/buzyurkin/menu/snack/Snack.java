package ru.nsu.buzyurkin.menu.snack;

import ru.nsu.buzyurkin.menu.MenuItem;

///**
// * Represents a snack item on the menu.
// * @param snackMenu the snack menu associated with this snack item
// */
public record Snack(SnackMenu snackMenu) implements MenuItem {}
