package ru.nsu.buzyurkin;

import java.util.*;

/**
 * This class represents an implementation of a graph using adjacency lists.
 *
 * @param <V> The type of values stored in the graph's nodes.
 * @param <W> The type of weights associated with the graph's edges (must be a subclass of Number).
 */
public class AdjacencyListsGraph<V, W extends Number> implements Graph<V, W> {
    /**
     * A map that represents the adjacency lists for each node in the graph.
     * The keys are nodes, and the values are lists of pairs consisting of target nodes and corresponding edges.
     */
    private final Map<Node<V>, List<Pair<Node<V>, Edge<W>>>> adjacencyLists;

    /**
     * Constructs an empty AdjacencyListsGraph.
     */
    public AdjacencyListsGraph() {
        this.adjacencyLists = new HashMap<>();
    }

    /**
     * Adds an edge between two nodes with the specified weight.
     *
     * @param u The source node.
     * @param v The target node.
     * @param weight The weight of the edge.
     * @return The created edge, or null if either of the nodes is null.
     */
    @Override
    public Edge<W> addEdge(Node<V> u, Node<V> v, W weight) {
        if (u == null || v == null) {
            return null;
        }

        Edge<W> newEdge = new Edge<>(weight);

        adjacencyLists.get(u).add(new Pair<>(v, newEdge));

        return newEdge;
    }

    /**
     * Retrieves the edge between two nodes, if it exists.
     *
     * @param u The source node.
     * @param v The target node.
     * @return The edge between the specified nodes, or null if it does not exist or if u is null.
     */
    @Override
    public Edge<W> getEdge(Node<V> u, Node<V> v) {

        if (adjacencyLists.get(u) == null) {
            return null;
        }

        for (Pair<Node<V>, Edge<W>> pair : adjacencyLists.get(u)) {
            if (pair.getLeft().equals(v)) {
                return pair.getRight();
            }
        }

        return null;
    }

    /**
     * Retrieves a list of all edges in the graph.
     *
     * @return A list of all edges in the graph.
     */
    @Override
    public List<Edge<W>> getEdges() {
        List<Edge<W>> edges = new ArrayList<>();

        for (Node<V> node : adjacencyLists.keySet()) {
            for (Pair<Node<V>, Edge<W>> pair : adjacencyLists.get(node)) {
                edges.add(pair.getRight());
            }
        }

        return edges;
    }

    /**
     * Retrieves a list of outgoing edges from the specified node, along with the corresponding target nodes.
     *
     * @param node The node for which outgoing edges are to be retrieved.
     * @return A list of pairs containing the target node and the outgoing edge from the specified node.
     */
    public List<Pair<Node<V>, Edge<W>>> getOutgoingEdges(Node<V> node) {

        return adjacencyLists.get(node);
    }

    /**
     * Removes the edge between two nodes, if it exists.
     *
     * @param u The source node.
     * @param v The target node.
     * @return True if the edge was successfully removed, false if the edge does not exist or if u is null.
     */
    @Override
    public boolean removeEdge(Node<V> u, Node<V> v) {
        if (u == null || v == null) {
            return false;
        }

        for (Pair<Node<V>, Edge<W>> pair : adjacencyLists.get(u)) {
            if (pair.getLeft().equals(v)) {
                adjacencyLists.get(u).remove(pair);
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the count of edges in the graph.
     *
     * @return The count of edges in the graph.
     */
    @Override
    public int getEdgesCount() {
        int edgesCount = 0;

        for (Node<V> node : adjacencyLists.keySet()) {
            edgesCount += adjacencyLists.get(node).size();
        }

        return edgesCount;
    }

    /**
     * Adds a vertex to the graph with the specified value.
     *
     * @param value The value to be associated with the new vertex.
     * @return The newly created vertex.
     */
    @Override
    public Node<V> addVertex(V value) {
        Node<V> newNode = new Node<>(value);

        adjacencyLists.put(newNode, new ArrayList<>());

        return newNode;
    }

    /**
     * Retrieves a node with the specified value from the graph, if it exists.
     *
     * @param value The value of the node to be retrieved.
     * @return The node with the specified value, or null if it does not exist in the graph.
     */
    @Override
    public Node<V> getVertex(V value) {
        for (Node<V> node : adjacencyLists.keySet()) {
            if (node.getValue() == value) {
                return node;
            }
        }

        return null;
    }

    /**
     * Retrieves a list of all vertices in the graph.
     *
     * @return A list of all vertices in the graph.
     */
    @Override
    public List<Node<V>> getVertices() {
        return new ArrayList<>(adjacencyLists.keySet());
    }

    /**
     * Removes the specified vertex from the graph, along with all incident edges.
     *
     * @param node The node to be removed from the graph.
     * @return True if the vertex was successfully removed, false if the vertex does not exist or if node is null.
     */
    @Override
    public boolean removeVertex(Node<V> node) {
        if (node == null) {
            return false;
        }

        if (!adjacencyLists.containsKey(node)) {
            return false;
        }

        adjacencyLists.remove(node);

        return true;
    }

    /**
     * Retrieves the count of vertices in the graph.
     *
     * @return The count of vertices in the graph.
     */
    @Override
    public int getVertexCount() {
        return adjacencyLists.keySet().size();
    }
}
