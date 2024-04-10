package ru.nsu.buzyurkin.menu.pizza;

import ru.nsu.buzyurkin.menu.MenuItem;

/**
 * Represents a pizza item on the menu.
 */
public record Pizza(PizzaMenu pizzaMenu,
                    PizzaSize pizzaSize,
                    PizzaCrust pizzaCrust) implements MenuItem {}
