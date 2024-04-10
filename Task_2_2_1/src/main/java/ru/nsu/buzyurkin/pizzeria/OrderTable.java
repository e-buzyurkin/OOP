package ru.nsu.buzyurkin.pizzeria;

import java.util.HashMap;
import java.util.Map;
import ru.nsu.buzyurkin.menu.Order;

/**
 * Represents the order table in a pizzeria, responsible for managing orders.
 */
public class OrderTable {
    private Map<Integer, Order> allOrders;

    /**
     * Constructs an OrderTable object.
     */
    public OrderTable() {
        allOrders = new HashMap<>();
    }

    /**
     * Adds an order to the order table.
     *
     * @param id    The ID of the order.
     * @param order The order to be added.
     */
    public synchronized void add(int id, Order order) {
        allOrders.put(id, order);
    }

    /**
     * Retrieves an order from the order table by its ID.
     *
     * @param id The ID of the order to retrieve.
     * @return The order corresponding to the given ID.
     */
    public synchronized Order getById(int id) {
        return allOrders.get(id);
    }

    /**
     * Marks an order as finished and removes it from the order table.
     *
     * @param id The ID of the order to mark as finished.
     */
    public synchronized void markFinished(int id) {
        allOrders.remove(id);
    }

    /**
     * Checks if all orders in the order table have been finished.
     *
     * @return true if all orders have been finished, false otherwise.
     */
    public synchronized boolean allFinished() {
        return !allOrders.isEmpty();
    }
}
