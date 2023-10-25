package ru.nsu.buzyurkin;

import java.util.Comparator;

class NumberMethods<T extends Number & Comparable> implements Comparator<T> {
    public int compare( T a, T b ) {
        return a.compareTo( b );
    }

    public T plus(T a, T b) {
        return (T) Double.valueOf(a.doubleValue() + b.doubleValue());
    }
}