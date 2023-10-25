package ru.nsu.buzyurkin;

public class Edge<T extends Number> {
    private T weight;

    public Edge(T weight) {
        this.weight = weight;
    }

    public T getWeight() {
        return weight;
    }

    public void setWeight(T weight) {
        this.weight = weight;
    }
}
