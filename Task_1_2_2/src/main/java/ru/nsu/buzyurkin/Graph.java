package ru.nsu.buzyurkin;

import java.util.List;

/**
 * This interface represents a generic graph structure,
 *              allowing the manipulation of vertices and edges.
 *
 * @param <V> The type of values stored in the graph's nodes.
 * @param <W> The type of weights associated
 *              with the graph's edges (must be a subclass of Number).
 */

public interface Graph<V, W extends Number> {
    /**
     * Adds an edge between two nodes with the specified weight.
     *
     * @param u The source node.
     * @param v The target node.
     * @param weight The weight of the edge.
     * @return The created edge.
     */
    Edge<W> addEdge(Node<V> u, Node<V> v, W weight);

    /**
     * Retrieves the edge between two nodes, if it exists.
     *
     * @param u The source node.
     * @param v The target node.
     * @return The edge between the specified nodes, or null if it does not exist.
     */
    Edge<W> getEdge(Node<V> u, Node<V> v);

    /**
     * Retrieves a list of all edges in the graph.
     *
     * @return A list of all edges in the graph.
     */
    List<Edge<W>> getEdges();

    /**
     * Retrieves a list of outgoing edges from the specified node,
     *                          along with the corresponding target nodes.
     *
     * @param node The node for which outgoing edges are to be retrieved.
     * @return A list of pairs containing the target node
     *              and the outgoing edge from the specified node.
     */
    List<Pair<Node<V>, Edge<W>>> getOutgoingEdges(Node<V> node);

    /**
     * Removes the edge between two nodes, if it exists.
     *
     * @param u The source node.
     * @param v The target node.
     * @return True if the edge was successfully removed, false if the edge does not exist.
     */
    boolean removeEdge(Node<V> u, Node<V> v);

    /**
     * Retrieves the count of edges in the graph.
     *
     * @return The count of edges in the graph.
     */
    int getEdgesCount();

    /**
     * Adds a vertex to the graph with the specified value.
     *
     * @param value The value to be associated with the new vertex.
     * @return The newly created vertex.
     */
    Node<V> addVertex(V value);

    /**
     * Retrieves a node with the specified value from the graph, if it exists.
     *
     * @param value The value of the node to be retrieved.
     * @return The node with the specified value, or null if it does not exist in the graph.
     */
    Node<V> getVertex(V value);

    /**
     * Retrieves a list of all vertices in the graph.
     *
     * @return A list of all vertices in the graph.
     */
    List<Node<V>> getVertices();

    /**
     * Removes the specified vertex from the graph, along with all incident edges.
     *
     * @param node The node to be removed from the graph.
     * @return True if the vertex was successfully removed, false if the vertex does not exist.
     */
    boolean removeVertex(Node<V> node);

    /**
     * Retrieves the count of vertices in the graph.
     *
     * @return The count of vertices in the graph.
     */
    int getVertexCount();

    /**
     * Checks if the graph contains the specified edge.
     *
     * @param edge The edge to be checked for existence in the graph.
     * @return True if the edge is present in the graph, false otherwise.
     */
    default boolean contains(Edge<W> edge) {
        return getEdges().contains(edge);
    }

    /**
     * Checks if the graph contains the specified vertex.
     *
     * @param node The vertex to be checked for existence in the graph.
     * @return True if the vertex is present in the graph, false otherwise.
     */
    default boolean contains(Node<V> node) {
        return getVertices().contains(node);
    }
}
