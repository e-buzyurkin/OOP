package ru.nsu.buzyurkin.entity;

public class Cell {
    private int x;
    private int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Cell(Cell cell) {
        this.x = cell.x;
        this.y = cell.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set(Cell cell) {
        this.x = cell.x;
        this.y = cell.y;
    }

    public void add(Cell cell) {
        this.x += cell.x;
        this.y += cell.y;
    }

    public void fixBorders(int x, int y) {
        add(new Cell(x, y));

        this.x %= x;
        this.y %= y;
    }
}
