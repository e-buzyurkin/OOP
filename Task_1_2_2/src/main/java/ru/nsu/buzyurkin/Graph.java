package ru.nsu.buzyurkin;

import java.util.List;

public interface Graph<V, W extends Number> {
    Edge<W> addEdge(Node<V> u, Node<V> v, W weight);

    Edge<W> getEdge(Node <V> u, Node<V> v);

    List<Edge<W>> getEdges();

    List<Pair<Node<V>, Edge<W>>> getOutgoingEdges(Node<V> node);

    boolean removeEdge(Node<V> u, Node<V> v);

    int getEdgesCount();

    default boolean contains(Edge<W> edge) {
        return getEdges().contains(edge);
    }

    Node<V> addVertex(V value);

    Node<V> getVertex(V value);

    List<Node<V>> getVertices();


    boolean removeVertex(Node<V> node);

    int getVertexCount();

    default boolean contains(Node<V> node) {
        return getVertices().contains(node);
    }
}
