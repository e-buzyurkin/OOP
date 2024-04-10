package ru.nsu.buzyurkin.menu.drink;

import ru.nsu.buzyurkin.menu.MenuItem;

/**
 * Represents a drink item on the menu.
 */
public record Drink(DrinkMenu drinkMenu) implements MenuItem {}
