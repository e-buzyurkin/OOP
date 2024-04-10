package ru.nsu.buzyurkin.menu.pizza;

import ru.nsu.buzyurkin.menu.MenuItem;

///**
// * Represents a pizza item on the menu.
// * @param pizzaMenu the pizza menu associated with this pizza item
// * @param pizzaSize the size of the pizza
// * @param pizzaCrust the crust type of the pizza
// */
public record Pizza(PizzaMenu pizzaMenu,
                    PizzaSize pizzaSize,
                    PizzaCrust pizzaCrust) implements MenuItem {}
