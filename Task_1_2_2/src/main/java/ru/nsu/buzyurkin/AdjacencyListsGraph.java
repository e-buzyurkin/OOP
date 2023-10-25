package ru.nsu.buzyurkin;

import java.util.*;

public class AdjacencyListsGraph<V, W extends Number> implements Graph<V, W> {
    private final Map<Node<V>, List<Pair<Node<V>, Edge<W>>>> adjacencyLists;

    public AdjacencyListsGraph() {
        this.adjacencyLists = new HashMap<>();
    }

    @Override
    public Edge<W> addEdge(Node<V> u, Node<V> v, W weight) {
        Edge<W> newEdge = new Edge<>(weight);

        adjacencyLists.get(u).add(new Pair<>(v, newEdge));

        return newEdge;
    }

    @Override
    public Edge<W> getEdge(Node<V> u, Node<V> v) {
        for (Pair<Node<V>, Edge<W>> pair : adjacencyLists.get(u)) {
            if (pair.getLeft().equals(v)) {
                return pair.getRight();
            }
        }

        return null;
    }

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

    public List<Pair<Node<V>, Edge<W>>> getOutgoingEdges(Node<V> node) {

        return adjacencyLists.get(node);
    }

    @Override
    public boolean removeEdge(Node<V> u, Node<V> v) {
        for (Pair<Node<V>, Edge<W>> pair : adjacencyLists.get(u)) {
            if (pair.getLeft().equals(v)) {
                adjacencyLists.get(u).remove(pair);
                return true;
            }
        }
        return false;
    }

    @Override
    public int getEdgesCount() {
        int edgesCount = 0;

        for (Node<V> node : adjacencyLists.keySet()) {
            edgesCount += adjacencyLists.get(node).size();
        }

        return edgesCount;
    }

    @Override
    public Node<V> addVertex(V value) {
        Node<V> newNode = new Node<>(value);

        adjacencyLists.put(newNode, new ArrayList<>());

        return newNode;
    }

    @Override
    public Node<V> getVertex(V value) {
        for (Node<V> node : adjacencyLists.keySet()) {
            if (node.getValue() == value) {
                return node;
            }
        }

        return null;
    }

    @Override
    public List<Node<V>> getVertices() {
        return new ArrayList<>(adjacencyLists.keySet());
    }

    @Override
    public boolean removeVertex(Node<V> node) {
        if (!adjacencyLists.containsKey(node)) {
            return false;
        }

        adjacencyLists.remove(node);

        return true;
    }

    @Override
    public int getVertexCount() {
        return adjacencyLists.keySet().size();
    }
}
