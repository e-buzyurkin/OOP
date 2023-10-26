package ru.nsu.buzyurkin;

import java.util.*;

/**
 * This class represents an implementation of a graph using an incidence matrix.
 *
 * @param <V> The type of values stored in the graph's nodes.
 * @param <W> The type of weights associated with the graph's edges (must be a subclass of Number).
 */
public class IncidencyMatrixGraph<V, W extends Number> implements Graph<V, W> {
    /**
     * A map representing the incidence matrix for the graph.
     * The keys are nodes, and the values are maps that store edges and their corresponding directions.
     */
    private final Map<Node<V>, Map<Edge<W>, EdgeDirection>> incidencyMatrix;

    /**
     * An enumeration to represent edge directions in the incidence matrix.
     * INGOING represents an incoming edge to a node, OUTGOING represents an outgoing edge, and NO_EDGE represents no edge.
     */
    private enum EdgeDirection {
        INGOING,
        NO_EDGE,
        OUTGOING
    }

    /**
     * Constructs an empty IncidencyMatrixGraph.
     */
    public IncidencyMatrixGraph() {
        incidencyMatrix = new HashMap<>();
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

        for (Node<V> node : incidencyMatrix.keySet()) {
            incidencyMatrix.get(node).put(newEdge, EdgeDirection.NO_EDGE);
        }

        incidencyMatrix.get(u).replace(newEdge, EdgeDirection.OUTGOING);
        incidencyMatrix.get(v).replace(newEdge, EdgeDirection.INGOING);

        return newEdge;
    }

    /**
     * Retrieves the edge between two nodes, if it exists.
     *
     * @param u The source node.
     * @param v The target node.
     * @return The edge between the specified nodes, or null if it does not exist or if u or v is null.
     */
    @Override
    public Edge<W> getEdge(Node<V> u, Node<V> v) {

        if (incidencyMatrix.get(u) == null) {
            return null;
        }

        for (Edge<W> edge : incidencyMatrix.get(u).keySet()) {
            if (incidencyMatrix.get(u).get(edge) == EdgeDirection.OUTGOING
             && incidencyMatrix.get(v).get(edge) == EdgeDirection.INGOING)
            {
                return edge;
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

        for (Node<V> node: incidencyMatrix.keySet()) {
            for (Edge<W> edge: incidencyMatrix.get(node).keySet()) {
                if (incidencyMatrix.get(node).get(edge) == EdgeDirection.OUTGOING) {
                    edges.add(edge);
                }
            }
        }

        return edges;
    }

    /**
     * Retrieves a list of outgoing edges from the specified node, along with the corresponding target nodes.
     *
     * @param node The node for which outgoing edges are to be retrieved.
     * @return A list of pairs containing the target node and the outgoing edge from the specified node,
     *         or null if the node is not found in the graph.
     */
    @Override
    public List<Pair<Node<V>, Edge<W>>> getOutgoingEdges(Node<V> node) {

        if (incidencyMatrix.get(node) == null) {
            return null;
        }

        List<Pair<Node<V>, Edge<W>>> edges = new ArrayList<>();

        for (Edge<W> edge : incidencyMatrix.get(node).keySet()) {
            if (incidencyMatrix.get(node).get(edge) == EdgeDirection.OUTGOING) {

                for (Node<V> vert : incidencyMatrix.keySet()) {
                    if (incidencyMatrix.get(vert).get(edge) == EdgeDirection.INGOING) {
                        edges.add(new Pair<>(vert, edge));
                        break;
                    }
                }

            }
        }

        return edges;
    }

    /**
     * Removes the edge between two nodes, if it exists.
     *
     * @param u The source node.
     * @param v The target node.
     * @return True if the edge was successfully removed, false if the edge does not exist or if u or v is null.
     */
    @Override
    public boolean removeEdge(Node<V> u, Node<V> v) {
        if (u == null || v == null) {
            return false;
        }

        Edge<W> edge = getEdge(u, v);

        if (edge == null) {
            return false;
        }

        incidencyMatrix.get(u).remove(edge);
        incidencyMatrix.get(v).remove(edge);

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
        Node<V> newNode = new Node<>(value);

        incidencyMatrix.put(newNode, new HashMap<>());

        List<Edge<W>> edges = getEdges();

        for (Edge<W> edge : edges) {
            incidencyMatrix.get(newNode).put(edge, EdgeDirection.NO_EDGE);
        }

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
        for (Node<V> node : incidencyMatrix.keySet()) {
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
        return new ArrayList<>(incidencyMatrix.keySet());
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

        if (incidencyMatrix.get(node) == null) {
            return false;
        }

        incidencyMatrix.remove(node);

        return true;
    }

    /**
     * Retrieves the count of vertices in the graph.
     *
     * @return The count of vertices in the graph.
     */
    @Override
    public int getVertexCount() {
        return incidencyMatrix.keySet().size();
    }
}
