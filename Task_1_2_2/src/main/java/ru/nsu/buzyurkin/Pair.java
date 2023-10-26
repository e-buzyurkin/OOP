package ru.nsu.buzyurkin;

/**
 * This class represents a simple pair of two values,
 *  typically used to store a left and a right value.
 *
 * @param <L> The type of the left value.
 * @param <R> The type of the right value.
 */
public class Pair<L, R> {
    /**
     * The left value of the pair.
     */
    private L left;

    /**
     * The right value of the pair.
     */
    private R right;

    /**
     * Constructs a new pair with the specified left and right values.
     *
     * @param left The left value of the pair.
     * @param right The right value of the pair.
     */
    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Retrieves the left value of the pair.
     *
     * @return The left value stored in the pair.
     */
    public L getLeft() {
        return this.left;
    }

    /**
     * Sets the left value of the pair.
     *
     * @param left The new left value to be associated with the pair.
     */
    public void setLeft(L left) {
        this.left = left;
    }

    /**
     * Retrieves the right value of the pair.
     *
     * @return The right value stored in the pair.
     */
    public R getRight() {
        return this.right;
    }

    /**
     * Sets the right value of the pair.
     *
     * @param right The new right value to be associated with the pair.
     */
    public void setRight(R right) {
        this.right = right;
    }
}
