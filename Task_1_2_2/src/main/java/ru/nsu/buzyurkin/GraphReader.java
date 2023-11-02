package ru.nsu.buzyurkin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class provides methods for reading a graph
 *          from a file and populating a graph data structure.
 */
public class GraphReader {

    /**
     * Reads a graph from a file and populates the provided graph data structure.
     *
     * @param filename The name of the file containing the graph data.
     * @param graph The graph data structure to populate with the graph data.
     * @throws FileNotFoundException If the specified file is not found.
     */
    static void readGraphFromFile(String filename, Graph<Integer, Integer> graph) {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            return;
        }

        int nodesCount = scanner.nextInt();
        int edgesCount = scanner.nextInt();

        for (int i = 1; i <= nodesCount; i++) {
            graph.addVertex(i);
        }

        for (int i = 0; i < edgesCount; i++) {
            Node<Integer> u = graph.getVertex(scanner.nextInt());
            Node<Integer> v = graph.getVertex(scanner.nextInt());
            int w = scanner.nextInt();

            graph.addEdge(u, v, w);
        }
    }

}