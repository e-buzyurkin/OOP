package ru.nsu.buzyurkin;

import java.util.*;

public class IncidencyMatrixGraph<V, W extends Number> implements Graph<V, W> {
    private final Map<Node<V>, Map<Edge<W>, EdgeDirection>> incidencyMatrix;
    private enum EdgeDirection {
        INGOING,
        NO_EDGE,
        OUTGOING
    }

    public IncidencyMatrixGraph() {
        incidencyMatrix = new HashMap<>();
    }

    @Override
    public Edge<W> addEdge(Node<V> u, Node<V> v, W weight) {
        Edge<W> newEdge = new Edge<>(weight);

        incidencyMatrix.get(u).put(newEdge, EdgeDirection.OUTGOING);
        incidencyMatrix.get(v).put(newEdge, EdgeDirection.INGOING);

        for (Node<V> node : incidencyMatrix.keySet()) {
            incidencyMatrix.get(node).put(newEdge, EdgeDirection.NO_EDGE);
        }

        return newEdge;
    }

    @Override
    public Edge<W> getEdge(Node<V> u, Node<V> v) {
        for (Edge<W> edge : incidencyMatrix.get(u).keySet()) {
            if (incidencyMatrix.get(u).get(edge) == EdgeDirection.OUTGOING
             && incidencyMatrix.get(v).get(edge) == EdgeDirection.INGOING)
            {
                return edge;
            }
        }

        return null;
    }

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

    @Override
    public List<Pair<Node<V>, Edge<W>>> getOutgoingEdges(Node<V> node) {
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

    @Override
    public boolean removeEdge(Node<V> u, Node<V> v) {
        Edge<W> edge = getEdge(u, v);

        if (edge == null) {
            return false;
        }

        incidencyMatrix.get(u).remove(edge);
        incidencyMatrix.get(v).remove(edge);

        return true;
    }

    @Override
    public int getEdgesCount() {
        return getEdges().size();
    }

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

    @Override
    public Node<V> getVertex(V value) {
        for (Node<V> node : incidencyMatrix.keySet()) {
            if (node.getValue() == value) {
                return node;
            }
        }

        return null;
    }

    @Override
    public List<Node<V>> getVertices() {
        return new ArrayList<>(incidencyMatrix.keySet());
    }

    @Override
    public boolean removeVertex(Node<V> node) {
        if (incidencyMatrix.get(node) != null) {
            return false;
        }

        incidencyMatrix.remove(node);

        return true;
    }

    @Override
    public int getVertexCount() {
        return incidencyMatrix.keySet().size();
    }
}
