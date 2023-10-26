package ru.nsu.buzyurkin;

/**
 * This class represents an edge in a graph with a weight.
 *
 * @param <T> The type of weight associated with the edge (must be a subclass of Number).
 */
public class Edge<T extends Number> {
    /**
     * The weight associated with the edge.
     */
    private T weight;

    /**
     * Constructs a new edge with the specified weight.
     *
     * @param weight The weight to be associated with the edge.
     */
    public Edge(T weight) {
        this.weight = weight;
    }

    /**
     * Retrieves the weight associated with the edge.
     *
     * @return The weight of the edge.
     */
    public T getWeight() {
        return weight;
    }

    /**
     * Sets the weight associated with the edge to a new value.
     *
     * @param weight The new weight to be associated with the edge.
     */
    public void setWeight(T weight) {
        this.weight = weight;
    }
}
