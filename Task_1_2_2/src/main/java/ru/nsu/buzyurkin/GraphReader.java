package ru.nsu.buzyurkin;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.*;

public class GraphReader {

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
            Node<Integer> u = new Node<>(scanner.nextInt());
            Node<Integer> v = new Node<>(scanner.nextInt());
            int w = scanner.nextInt();

            graph.addEdge(u, v, w);
        }
    }

}