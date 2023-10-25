package ru.nsu.buzyurkin;

import java.util.*;

public class GraphMethods {
    private NumberMethods numbers;
    public <V, W extends Number> Map<Node<V>, W> dijkstra(Graph<V, W> graph, Node<V> source) {

        Map<Node<V>, W> dist = new HashMap<>();
        Set<Node<V>> visited = new HashSet<>();

        dist.put(source, (W) Double.valueOf(0));

        for (Node<V> node : graph.getVertices()) {
            dist.put(node, (W) Double.valueOf(Double.POSITIVE_INFINITY));
        }

        for (Node<V> node : graph.getVertices()) {
            Node<V> minNode = null;

            for (Node<V> curNode : graph.getVertices()) {
                W v1 = dist.get(curNode);
                W v2 = dist.get(minNode);

                if (!visited.contains(curNode) &&
                        (minNode == null || numbers.compare(v1, v2) < 0)) {
                    minNode = curNode;
                }
            }

            if (dist.get(minNode) == (W) Double.valueOf(Double.POSITIVE_INFINITY)) {
                break;
            }

            visited.add(minNode);

            for (Pair<Node<V>, Edge<W>> edgePair : graph.getOutgoingEdges(minNode)) {
                W newDist = (W) numbers.plus(dist.get(minNode), edgePair.getRight().getWeight());
                W curDist = dist.get(edgePair.getLeft());

                if (numbers.compare(newDist, curDist) < 0) {
                    dist.replace(edgePair.getLeft(), newDist);
                }
            }
        }

        return dist;
    }
}
