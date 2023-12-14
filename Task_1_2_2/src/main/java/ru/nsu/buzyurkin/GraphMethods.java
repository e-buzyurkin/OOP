package ru.nsu.buzyurkin;

import java.util.*;

/**
 * This class provides utility methods for working with graphs,
 *      including Dijkstra's algorithm for finding the shortest paths.
 */
public class GraphMethods {

    /**
     * Performs Dijkstra's algorithm to find the shortest paths
     *          from a source node to all other nodes in the graph.
     *
     * @param <V> The type of values stored in the graph's nodes.
     * @param <W> The type of weights associated with the graph's edges
     *              (must be a subclass of Number).
     * @param graph The graph on which Dijkstra's algorithm will be applied.
     * @param source The source node from which the shortest paths are calculated.
     * @return A map that associates each node in the graph
     *         with the shortest distance from the source node,
     *         or Integer.MAX_VALUE for nodes that are not reachable from the source.
     */
    static <V, W extends Number> Map<Node<V>, W> dijkstra(Graph<V, W> graph, Node<V> source) {
        NumberMethods numbers = new NumberMethods();

        Map<Node<V>, W> dist = new HashMap<>();
        Set<Node<V>> visited = new HashSet<>();

        for (Node<V> node : graph.getVertices()) {
            dist.put(node, (W) Integer.valueOf(Integer.MAX_VALUE));
        }

        dist.replace(source, (W) Integer.valueOf(0));

        for (Node<V> node : graph.getVertices()) {
            Node<V> minNode = null;

            for (Node<V> curNode : graph.getVertices()) {
                W v1 = dist.get(curNode);
                W v2 = dist.get(minNode);

                if (!visited.contains(curNode)) {
                    if (minNode == null || numbers.compare(v1, v2) < 0) {
                        minNode = curNode;
                    }
                }
            }

            if (dist.get(minNode) == (W) Integer.valueOf(Integer.MAX_VALUE)) {
                break;
            }

            visited.add(minNode);

            for (Pair<Node<V>, Edge<W>> edgePair : graph.getOutgoingEdges(minNode)) {
                W newDist = (W) numbers.plus(dist.get(minNode), edgePair.getRight().getWeight());
                W curDist = (W) dist.get(edgePair.getLeft());

                if (numbers.compare(newDist, curDist) < 0) {
                    dist.replace(edgePair.getLeft(), newDist);
                }
            }
        }

        return dist;
    }

    /**
     * Performs BellmanFord's algorithm to find the shortest paths
     *          from a source node to all other nodes in the graph.
     *
     * @param <V> The type of values stored in the graph's nodes.
     * @param <W> The type of weights associated with the graph's edges
     *              (must be a subclass of Number).
     * @param graph The graph on which Dijkstra's algorithm will be applied.
     * @param source The source node from which the shortest paths are calculated.
     * @return A map that associates each node in the graph
     *         with the shortest distance from the source node,
     *         or Integer.MAX_VALUE for nodes that are not reachable from the source.
     */
    static <V, W extends Number> Map<Node<V>, W> bellmanford(Graph<V, W> graph, Node<V> source) {
        NumberMethods numbers = new NumberMethods();

        Map<Node<V>, W> dist = new HashMap<>();

        for (Node<V> node : graph.getVertices()) {
            dist.put(node, (W) Integer.valueOf(Integer.MAX_VALUE));
        }
        dist.replace(source, (W) Integer.valueOf(0));

        Map<Pair<Node<V>, Node<V>>, Edge<W>> edges = graph.getAllEdgesMap();

        for (int i = 0; i < graph.getVertexCount() - 1; i++) {
            for (Pair<Node<V>, Node<V>> nodePair : edges.keySet()) {
                W curDist = (W) dist.get(nodePair.getRight());
                W newDist = (W) numbers.plus(
                        dist.get(nodePair.getLeft()),
                        edges.get(nodePair).getWeight()
                );

                if (numbers.compare(newDist, curDist) < 0) {
                    dist.replace(nodePair.getRight(), newDist);
                }
            }
        }

        for (Pair<Node<V>, Node<V>> nodePair : edges.keySet()) {
            W curDist = (W) dist.get(nodePair.getRight());
            W newDist = (W) numbers.plus(
                    dist.get(nodePair.getLeft()),
                    edges.get(nodePair).getWeight()
            );

            if (numbers.compare(newDist, curDist) < 0) {
                return null;
            }
        }


        return dist;
    }
}
