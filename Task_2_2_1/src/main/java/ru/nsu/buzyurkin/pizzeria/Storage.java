package ru.nsu.buzyurkin.pizzeria;

import ru.nsu.buzyurkin.util.BlockingQueue;

/**
 * Represents the storage for orders in a pizzeria.
 */
public class Storage {
    private BlockingQueue<Integer> storage;

    /**
     * Constructs a Storage object with the specified capacity.
     *
     * @param capacity The capacity of the storage.
     */
    public Storage(int capacity) {
        storage = new BlockingQueue<>(capacity);
    }

    /**
     * Adds an order ID to the storage.
     *
     * @param id The ID of the order to add.
     * @throws InterruptedException if the operation is interrupted.
     */
    public void add(int id) throws InterruptedException {
        storage.push(id);
    }

    /**
     * Takes an order ID from the storage.
     *
     * @return The ID of the order taken.
     * @throws InterruptedException if the operation is interrupted.
     */
    public int take() throws InterruptedException {
        return storage.pop();
    }

    /**
     * Checks if the storage is empty.
     *
     * @return true if the storage is empty, false otherwise.
     */
    public boolean isEmpty() {
        return storage.isEmpty();
    }
}
