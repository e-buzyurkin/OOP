package ru.nsu.buzyurkin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents an implementation of a graph using an adjacency matrix.
 *
 * @param <V> The type of values stored in the graph's nodes.
 * @param <W> The type of weights associated with the graph's edges
 *                  (must be a subclass of Number).
 */
public class AdjacencyMatrixGraph<V, W extends Number> implements Graph<V, W> {
    /**
     * A map representing the adjacency matrix for the graph.
     * The keys are source nodes, and the values are maps
     *          that store target nodes and their corresponding edges.
     */
    private final Map<Node<V>, Map<Node<V>, Edge<W>>> adjacencyMatrix;

    /**
     * Constructs an empty AdjacencyMatrixGraph.
     */
    public AdjacencyMatrixGraph() {
        adjacencyMatrix = new HashMap<>();
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

        adjacencyMatrix.get(u).put(v, newEdge);

        return newEdge;
    }

    /**
     * Retrieves the edge between two nodes, if it exists.
     *
     * @param u The source node.
     * @param v The target node.
     * @return The edge between the specified nodes,
     *          or null if it does not exist or if u or v is null.
     */
    @Override
    public Edge<W> getEdge(Node<V> u, Node<V> v) {
        if (u == null || v == null) {
            return null;
        }

        if (adjacencyMatrix.get(u) == null) {
            return null;
        }

        return adjacencyMatrix.get(u).get(v);
    }

    /**
     * Retrieves a list of all edges in the graph.
     *
     * @return A list of all edges in the graph.
     */
    @Override
    public List<Edge<W>> getEdges() {
        List<Edge<W>> edges = new ArrayList<>();

        for (Node<V> i : adjacencyMatrix.keySet()) {
            for (Node<V> j : adjacencyMatrix.get(i).keySet()) {
                if (adjacencyMatrix.get(i).get(j) != null) {
                    edges.add(adjacencyMatrix.get(i).get(j));
                }
            }
        }

        return edges;
    }

    /**
     * Retrieves a mapping of all edges in the graph, where the keys are pairs of nodes
     * representing the source and target nodes of each edge,
     *          and the values are the corresponding edges.
     *
     * @return A map containing pairs of nodes as keys and their corresponding edges as values,
     *         representing all edges in the graph.
     */
    public Map<Pair<Node<V>, Node<V>>, Edge<W>> getAllEdgesMap() {
        Map<Pair<Node<V>, Node<V>>, Edge<W>> edges = new HashMap<>();

        for (Node<V> i : adjacencyMatrix.keySet()) {
            for (Node<V> j : adjacencyMatrix.get(i).keySet()) {
                if (adjacencyMatrix.get(i).get(j) != null) {
                    Pair<Node<V>, Node<V>> nodePair = new Pair<>(i, j);

                    edges.put(nodePair, adjacencyMatrix.get(i).get(j));
                }
            }
        }

        return edges;
    }

    /**
     * Retrieves a list of outgoing edges from the specified node,
     *          along with the corresponding target nodes.
     *
     * @param node The node for which outgoing edges are to be retrieved.
     * @return A list of pairs containing the target node
     *          and the outgoing edge from the specified node,
     *         or null if the node is not found in the graph.
     */

    public List<Pair<Node<V>, Edge<W>>> getOutgoingEdges(Node<V> node) {
        List<Pair<Node<V>, Edge<W>>> edges = new ArrayList<>();

        if (adjacencyMatrix.get(node) == null) {
            return null;
        }

        for (Node<V> vert : adjacencyMatrix.get(node).keySet()) {
            Edge<W> edge = adjacencyMatrix.get(node).get(vert);

            if (edge != null) {
                edges.add(new Pair<>(vert, edge));
            }
        }

        return edges;
    }

    /**
     * Removes the edge between two nodes, if it exists.
     *
     * @param u The source node.
     * @param v The target node.
     * @return True if the edge was successfully removed,
     *          false if the edge does not exist or if u or v is null.
     */
    @Override
    public boolean removeEdge(Node<V> u, Node<V> v) {
        if (u == null || v == null) {
            return false;
        }

        if (adjacencyMatrix.get(u).get(v) == null) {
            return false;
        }

        adjacencyMatrix.get(u).replace(v, null);

        return true;
    }

    /**
     * Retrieves the count of edges in the graph.
     *
     * @return The count of edges in the graph.
     */
    @Override
    public int getEdgesCount() {
        return getEdges().size();
    }

    /**
     * Adds a vertex to the graph with the specified value.
     *
     * @param value The value to be associated with the new vertex.
     * @return The newly created vertex.
     */
    @Override
    public Node<V> addVertex(V value) {

        Node<V> newVertex = new Node<>(value);

        for (Node<V> vertex : adjacencyMatrix.keySet()) {
            adjacencyMatrix.get(vertex).put(newVertex, null);
        }

        adjacencyMatrix.put(newVertex, new HashMap<>());

        for (Node<V> vertex : adjacencyMatrix.keySet()) {
            adjacencyMatrix.get(newVertex).put(vertex, null);
        }

        return newVertex;
    }

    /**
     * Retrieves a node with the specified value from the graph, if it exists.
     *
     * @param value The value of the node to be retrieved.
     * @return The node with the specified value, or null if it does not exist in the graph.
     */
    @Override
    public Node<V> getVertex(V value) {
        for (Node<V> node : adjacencyMatrix.keySet()) {
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
        return new ArrayList<>(adjacencyMatrix.keySet());
    }

    /**
     * Removes the specified vertex from the graph, along with all incident edges.
     *
     * @param node The node to be removed from the graph.
     * @return True if the vertex was successfully removed,
     *      false if the vertex does not exist or if node is null.
     */
    @Override
    public boolean removeVertex(Node<V> node) {
        if (node == null) {
            return false;
        }

        if (adjacencyMatrix.get(node) == null) {
            return false;
        }

        for (Node<V> vertex : adjacencyMatrix.keySet()) {
            adjacencyMatrix.get(vertex).remove(node);
        }

        adjacencyMatrix.remove(node);

        return true;
    }

    /**
     * Retrieves the count of vertices in the graph.
     *
     * @return The count of vertices in the graph.
     */
    @Override
    public int getVertexCount() {
        return adjacencyMatrix.size();
    }
}
