package ru.nsu.buzyurkin;

/**
 * This class represents a node in a graph.
 *
 * @param <T> The type of value stored in the node.
 */
public class Node<T> {
    /**
     * The value associated with the node.
     */
    private T value;

    /**
     * Constructs a new node with the specified value.
     *
     * @param value The value to be associated with the node.
     */
    public Node(T value) {
        this.value = value;
    }

    /**
     * Retrieves the value associated with the node.
     *
     * @return The value stored in the node.
     */
    public T getValue() {
        return this.value;
    }

    /**
     * Sets the value associated with the node.
     *
     * @param value The new value to be associated with the node.
     */
    public void setValue(T value) {
        this.value = value;
    }
}
