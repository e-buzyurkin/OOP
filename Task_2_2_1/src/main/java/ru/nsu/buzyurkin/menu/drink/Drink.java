package ru.nsu.buzyurkin.menu.drink;

import ru.nsu.buzyurkin.menu.MenuItem;

/**
 * Represents a drink item on the menu.
 * @param drinkMenu the drink menu associated with this drink item
 */
public record Drink(DrinkMenu drinkMenu) implements MenuItem {}
