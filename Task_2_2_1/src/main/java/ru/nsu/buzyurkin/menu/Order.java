package ru.nsu.buzyurkin.menu;

import java.util.List;

///**
// * Represents an order containing a list of menu items.
// * @param order the list of menu items included in the order
// */
public record Order(List<MenuItem> order) {}
