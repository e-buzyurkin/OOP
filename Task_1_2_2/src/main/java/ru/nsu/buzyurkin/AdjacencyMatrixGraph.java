package ru.nsu.buzyurkin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyMatrixGraph<V, W extends Number> implements Graph<V, W> {
    private final Map<Node<V>, Map<Node<V>, Edge<W>>> adjacencyMatrix;

    public AdjacencyMatrixGraph() {
        adjacencyMatrix = new HashMap<>();
    }

    @Override
    public Edge<W> addEdge(Node<V> u, Node<V> v, W weight) {
        Edge<W> newEdge = new Edge<>(weight);

        adjacencyMatrix.get(u).put(v, newEdge);

        return newEdge;
    }

    @Override
    public Edge<W> getEdge(Node<V> u, Node<V> v) {
        return adjacencyMatrix.get(u).get(v);
    }

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

    public List<Pair<Node<V>, Edge<W>>> getOutgoingEdges(Node<V> node) {
        List<Pair<Node<V>, Edge<W>>> edges = new ArrayList<>();

        for (Node<V> vert : adjacencyMatrix.get(node).keySet()) {
            Edge<W> edge = adjacencyMatrix.get(node).get(vert);

            if (edge != null) {
                edges.add(new Pair<>(vert, edge));
            }
        }

        return edges;
    }

    @Override
    public boolean removeEdge(Node<V> u, Node<V> v) {

        if (adjacencyMatrix.get(u).get(v) == null) {
            return false;
        }

        adjacencyMatrix.get(u).replace(v, null);

        return true;
    }

    @Override
    public int getEdgesCount() {
        return getEdges().size();
    }

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

    @Override
    public Node<V> getVertex(V value) {
        for (Node<V> node : adjacencyMatrix.keySet()) {
            if (node.getValue() == value) {
                return node;
            }
        }

        return null;
    }

    @Override
    public List<Node<V>> getVertices() {
        return new ArrayList<>(adjacencyMatrix.keySet());
    }

    @Override
    public boolean removeVertex(Node<V> node) {

        if (adjacencyMatrix.get(node) == null) {
            return false;
        }

        for (Node<V> vertex : adjacencyMatrix.keySet()) {
            adjacencyMatrix.get(vertex).remove(node);
        }

        adjacencyMatrix.remove(node);

        return true;
    }

    @Override
    public int getVertexCount() {
        return adjacencyMatrix.size();
    }
}
