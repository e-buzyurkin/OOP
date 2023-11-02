package ru.nsu.buzyurkin;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains unit tests for various graph operations and algorithms.
 */
public class GraphTests {
    private final Graph<Integer, Integer> adjListsGraph = new AdjacencyListsGraph<>();
    private final Graph<Integer, Integer> incMatrixGraph = new IncidencyMatrixGraph<>();
    private final Graph<Integer, Integer> adjMatrixGraph = new AdjacencyMatrixGraph<>();

    /**
     * Reads graphs from files before running each test.
     */
    @BeforeEach
    public void readGraphs() {
        String filepath = System.getProperty("user.dir");
        GraphReader.readGraphFromFile("./src/test/resources/input.txt", adjListsGraph);
        GraphReader.readGraphFromFile("./src/test/resources/input.txt", incMatrixGraph);
        GraphReader.readGraphFromFile("./src/test/resources/input.txt", adjMatrixGraph);
    }

    /**
     * Tests whether different representations of the same graph are equal.
     */
    @Test
    void testEqualRepresentations() {
        assertTrue(equalGraphs(adjListsGraph, incMatrixGraph));
        assertTrue(equalGraphs(adjListsGraph, adjMatrixGraph));
        assertTrue(equalGraphs(incMatrixGraph, adjMatrixGraph));
    }

    /**
     * Tests edge weights in the adjacency lists representation of the graph.
     */
    @Test
    void testAdjListsWeights() {
        var node3 = adjListsGraph.getVertex(3);
        var node7 = adjListsGraph.getVertex(7);
        assertEquals(2, adjListsGraph.getEdge(node3, node7).getWeight());
        assertNull(adjListsGraph.getEdge(node7, node3));
    }

    /**
     * Tests edge weights in the adjacency matrix representation of the graph.
     */
    @Test
    void testAdjMatrixWeights() {
        var node3 = adjMatrixGraph.getVertex(3);
        var node7 = adjMatrixGraph.getVertex(7);
        assertEquals(2, adjMatrixGraph.getEdge(node3, node7).getWeight());
        assertNull(adjMatrixGraph.getEdge(node7, node3));
    }

    /**
     * Tests edge weights in the incidency matrix representation of the graph.
     */
    @Test
    void testIncMatrixWeights() {
        var node3 = incMatrixGraph.getVertex(3);
        var node7 = incMatrixGraph.getVertex(7);
        assertEquals(2, incMatrixGraph.getEdge(node3, node7).getWeight());
        assertNull(incMatrixGraph.getEdge(node7, node3));
    }

    /**
     * Tests edges in the adjacency lists representation of the graph.
     */
    @Test
    void testAdjListsEdges() {
        List<Edge<Integer>> edges = adjListsGraph.getEdges();

        var edgesValues = edges.stream().map(Edge::getWeight).collect(Collectors.toList());
        List<Integer> correctEdges = List.of(2, 1, 1, 2, 1, 1);
        assertTrue(equalLists(correctEdges, edgesValues));
    }

    /**
     * Tests edges in the adjacency matrix representation of the graph.
     */
    @Test
    void testAdjMatrixEdges() {
        List<Edge<Integer>> edges = adjMatrixGraph.getEdges();

        var edgesValues = edges.stream().map(Edge::getWeight).collect(Collectors.toList());
        List<Integer> correctEdges = List.of(2, 1, 1, 2, 1, 1);
        assertTrue(equalLists(correctEdges, edgesValues));
    }

    /**
     * Tests edges in the incidency matrix representation of the graph.
     */
    @Test
    void testIncMatrixEdges() {
        List<Edge<Integer>> edges = incMatrixGraph.getEdges();

        var edgesValues = edges.stream().map(Edge::getWeight).collect(Collectors.toList());
        List<Integer> correctEdges = List.of(2, 1, 1, 2, 1, 1);
        assertTrue(equalLists(correctEdges, edgesValues));
    }

    /**
     * Tests removal of edges and vertices in the adjacency lists representation of the graph.
     */
    @Test
    void testAdjListsRemoves() {
        var node3 = adjListsGraph.getVertex(3);
        var node7 = adjListsGraph.getVertex(7);

        assertTrue(adjListsGraph.removeEdge(node3, node7));
        assertFalse(adjListsGraph.removeEdge(node7, node3));

        assertNull(adjListsGraph.getEdge(node3, node7));

        assertTrue(adjListsGraph.removeVertex(node3));
        assertFalse(adjListsGraph.removeVertex(node3));

        assertNull(adjListsGraph.getEdge(node3, node7));
    }

    /**
     * Tests removal of edges and vertices in the adjacency matrix representation of the graph.
     */
    @Test
    void testAdjMatrixRemoves() {
        var node3 = adjMatrixGraph.getVertex(3);
        var node7 = adjMatrixGraph.getVertex(7);

        assertTrue(adjMatrixGraph.removeEdge(node3, node7));
        assertFalse(adjMatrixGraph.removeEdge(node7, node3));

        assertNull(adjMatrixGraph.getEdge(node3, node7));

        assertTrue(adjMatrixGraph.removeVertex(node3));
        assertFalse(adjMatrixGraph.removeVertex(node3));

        assertNull(adjMatrixGraph.getEdge(node3, node7));
    }

    /**
     * Tests removal of edges and vertices in the incidency matrix representation of the graph.
     */
    @Test
    void testIncMatrixRemoves() {
        var node3 = incMatrixGraph.getVertex(3);
        var node7 = incMatrixGraph.getVertex(7);

        assertTrue(incMatrixGraph.removeEdge(node3, node7));
        assertFalse(incMatrixGraph.removeEdge(node7, node3));

        assertNull(incMatrixGraph.getEdge(node3, node7));

        assertTrue(incMatrixGraph.removeVertex(node3));
        assertFalse(incMatrixGraph.removeVertex(node3));

        assertNull(incMatrixGraph.getEdge(node3, node7));
    }

    /**
     * Tests Dijkstra's algorithm on the adjacency lists representation of the graph.
     */
    @Test
    void testDijkstraAdjLists() {
        var node1 = adjListsGraph.getVertex(1);
        Map<Node<Integer>, Integer> distances = GraphMethods.dijkstra(adjListsGraph, node1);

        assertEquals(distances.get(node1), 0);
        var node5 = adjListsGraph.getVertex(5);
        var inf = Double.POSITIVE_INFINITY;
        assertEquals(distances.get(node5), (int) inf);
    }

    /**
     * Tests Dijkstra's algorithm on the adjacency matrix representation of the graph.
     */
    @Test
    void testDijkstraAdjMatrix() {
        var node1 = adjMatrixGraph.getVertex(1);
        Map<Node<Integer>, Integer> distances = GraphMethods.dijkstra(adjMatrixGraph, node1);

        assertEquals(distances.get(node1), 0);
        var node5 = adjMatrixGraph.getVertex(5);
        var inf = Double.POSITIVE_INFINITY;
        assertEquals(distances.get(node5), (int) inf);
    }

    /**
     * Tests Dijkstra's algorithm on the incidency matrix representation of the graph.
     */
    @Test
    void testDijkstraIncMatrix() {
        var node1 = incMatrixGraph.getVertex(1);
        Map<Node<Integer>, Integer> distances = GraphMethods.dijkstra(incMatrixGraph, node1);

        assertEquals(distances.get(node1), 0);
        var node5 = incMatrixGraph.getVertex(5);
        var inf = Double.POSITIVE_INFINITY;
        assertEquals(distances.get(node5), (int) inf);
    }


    /**
     * Checks if two lists are equal in terms of their elements.
     *
     * @param arr1 The first list to compare.
     * @param arr2 The second list to compare.
     * @return True if the lists have the same elements, false otherwise.
     */
    private boolean equalLists(List<Integer> arr1, List<Integer> arr2) {
        HashSet<Integer> set1 = new HashSet<Integer>(arr1);
        HashSet<Integer> set2 = new HashSet<Integer>(arr2);
        return set1.equals(set2);
    }

    /**
     * Compares two graphs to check if they are equal in terms of vertices and edges.
     *
     * @param graph1 The first graph to compare.
     * @param graph2 The second graph to compare.
     * @return True if the graphs are equal, false otherwise.
     */
    private boolean equalGraphs(Graph<Integer, Integer> graph1, Graph<Integer, Integer> graph2) {
        if (!(graph1.getEdgesCount() == graph2.getEdgesCount())) {
            return false;
        }
        if (!(graph1.getVertexCount() == graph2.getVertexCount())) {
            return false;
        }

        List<Integer> graph1Vertices = graph1.getVertices()
                .stream().map(Node::getValue)
                .collect(Collectors.toList());
        List<Integer> graph2Vertices = graph2.getVertices()
                .stream().map(Node::getValue)
                .collect(Collectors.toList());

        if (!equalLists(graph1Vertices, graph2Vertices)) {
            return false;
        }

        for (Node<Integer> node1 : graph1.getVertices()) {
            for (Node<Integer> node2 : graph2.getVertices()) {

                if (node1.getValue() == node2.getValue()) {
                    List<Pair<Node<Integer>, Edge<Integer>>> node1Edges =
                            graph1.getOutgoingEdges(node1);

                    List<Pair<Node<Integer>, Edge<Integer>>> node2Edges =
                            graph2.getOutgoingEdges(node2);

                    List<Integer> node1EdgesNodes =
                            node1Edges.stream()
                                    .map((Pair<Node<Integer>, Edge<Integer>> pair) ->
                                            pair.getLeft().getValue())
                                    .collect(Collectors.toList());

                    List<Integer> node2EdgesNodes =
                            node2Edges.stream()
                                    .map((Pair<Node<Integer>, Edge<Integer>> pair) ->
                                            pair.getLeft().getValue())
                                    .collect(Collectors.toList());

                    if (!equalLists(node1EdgesNodes, node1EdgesNodes)) {
                        return false;
                    }

                    List<Integer> node1EdgesWeights =
                            node1Edges.stream()
                                    .map((Pair<Node<Integer>, Edge<Integer>> pair) ->
                                            pair.getRight().getWeight())
                                    .collect(Collectors.toList());

                    List<Integer> node2EdgesWeights =
                            node2Edges.stream()
                                    .map((Pair<Node<Integer>, Edge<Integer>> pair) ->
                                            pair.getRight().getWeight())
                                    .collect(Collectors.toList());

                    if (!equalLists(node1EdgesWeights, node1EdgesWeights)) {
                        return false;
                    }
                }

            }
        }

        return true;
    }
}
