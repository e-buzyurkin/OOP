package ru.nsu.buzyurkin.menu;

import java.util.List;

/**
 * Represents an order containing a list of menu items.
 */
public record Order(List<MenuItem> order) {
}
