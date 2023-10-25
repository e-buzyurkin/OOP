package ru.nsu.buzyurkin;


import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.stream.Collectors;

public class GraphTests {
    private Graph<Integer, Integer> adjListsGraph = new AdjacencyListsGraph<>();
    private Graph<Integer, Integer> incMatrixGraph = new IncidencyMatrixGraph<>();
    private Graph<Integer, Integer> adjMatrixGraph = new AdjacencyMatrixGraph<>();

    @BeforeEach
    public void readGraphs() {
        GraphReader.readGraphFromFile("input.txt", adjListsGraph);
        GraphReader.readGraphFromFile("input.txt", incMatrixGraph);
        GraphReader.readGraphFromFile("input.txt", adjMatrixGraph);
    }

    @Test
    void testEqualRepresentations() {
        assertTrue(equalGraphs(adjListsGraph, incMatrixGraph));
        assertTrue(equalGraphs(adjListsGraph, adjMatrixGraph));
        assertTrue(equalGraphs(incMatrixGraph, adjMatrixGraph));
    }

    @Test
    void testAdjListsWeights() {
        var node3 = adjListsGraph.getVertex(3);
        var node7 = adjListsGraph.getVertex(7);
        assertEquals(2, adjListsGraph.getEdge(node3, node7).getWeight());
        assertNull(adjListsGraph.getEdge(node7, node3));
    }

    @Test
    void testAdjMatrixWeights() {
        var node3 = adjMatrixGraph.getVertex(3);
        var node7 = adjMatrixGraph.getVertex(7);
        assertEquals(2, adjMatrixGraph.getEdge(node3, node7).getWeight());
        assertNull(adjMatrixGraph.getEdge(node7, node3));
    }

    @Test
    void testIncMatrixWeights() {
        var node3 = adjMatrixGraph.getVertex(3);
        var node7 = adjMatrixGraph.getVertex(7);
        assertEquals(2, adjMatrixGraph.getEdge(node3, node7).getWeight());
        assertNull(incMatrixGraph.getEdge(node7, node3));
    }



    private boolean equalLists(List<Integer> arr1, List<Integer> arr2) {
        HashSet<Integer> set1 = new HashSet<Integer>(arr1);
        HashSet<Integer> set2 = new HashSet<Integer>(arr2);
        return set1.equals(set2);
    }

    private boolean equalGraphs(Graph<Integer, Integer> graph1, Graph<Integer, Integer> graph2) {
        if (!(graph1.getEdgesCount() == graph2.getEdgesCount())) {
            return false;
        }
        if (!(graph1.getVertexCount() == graph2.getVertexCount())) {
            return false;
        }

        List<Integer> graph1Vertices = graph1.getVertices()
                                       .stream().map(Node::getValue)
                                       .toList();
        List<Integer> graph2Vertices = graph2.getVertices()
                                       .stream().map(Node::getValue)
                                       .toList();

        if (!equalLists(graph1Vertices, graph2Vertices)) {
            return false;
        }

        for (Node<Integer> node1 : graph1.getVertices()) {
            for (Node<Integer> node2 : graph2.getVertices()) {

                if (node1.getValue() == node2.getValue()) {
                    List<Pair<Node<Integer>, Edge<Integer>>> node1Edges =
                            graph1.getOutgoingEdges(node1);

                    List<Pair<Node<Integer>, Edge<Integer>>> node2Edges =
                            graph1.getOutgoingEdges(node2);

                    List<Integer> node1EdgesNodes =
                            node1Edges.stream()
                                    .map((Pair<Node<Integer>, Edge<Integer>> pair) ->
                                            pair.getLeft().getValue())
                                    .toList();

                    List<Integer> node2EdgesNodes =
                            node2Edges.stream()
                                    .map((Pair<Node<Integer>, Edge<Integer>> pair) ->
                                            pair.getLeft().getValue())
                                    .toList();

                    if (!equalLists(node1EdgesNodes, node1EdgesNodes)) {
                        return false;
                    }

                    List<Integer> node1EdgesWeights =
                            node1Edges.stream()
                                    .map((Pair<Node<Integer>, Edge<Integer>> pair) ->
                                            pair.getRight().getWeight())
                                    .toList();

                    List<Integer> node2EdgesWeights =
                            node2Edges.stream()
                                    .map((Pair<Node<Integer>, Edge<Integer>> pair) ->
                                            pair.getRight().getWeight())
                                    .toList();

                    if (!equalLists(node1EdgesWeights, node1EdgesWeights)) {
                        return false;
                    }
                }

            }
        }

        return true;
    }
}
