package ru.nsu.buzyurkin;

public class HeapSort {
    /**
     * Sorts array in ascending order.
     *
     * @param a The array of integers to be sorted.
     */
    public static void sort(int[] a) {
        int n = a.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(a, n, i);
        }

        for (int i = n - 1; i >= 0; i--) {
            int helper = a[0];
            a[0] = a[i];
            a[i] = helper;

            heapify(a, i, 0);
        }
    }

    /**
     * Heapify a subtree rooted with the given index to ensure it maintains the max heap property.
     *
     * @param a            The array containing the elements.
     * @param n            The size of the heap.
     * @param givenIndex The index of the current element to be considered as the root of the subtree.
     */
    private static void heapify(int[] a, int n, int givenIndex) {
        int maxIndex = givenIndex;
        int l = 2 * maxIndex + 1;
        int r = 2 * maxIndex + 2;

        if (l < n && a[l] > a[maxIndex]) {
            maxIndex = l;
        }

        if (r < n && a[r] > a[maxIndex]) {
            maxIndex = r;
        }

        if (maxIndex != givenIndex) {
            int helper = a[givenIndex];
            a[givenIndex] = a[maxIndex];
            a[maxIndex] = helper;

            heapify(a, n, maxIndex);
        }
    }
}
