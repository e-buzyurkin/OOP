package ru.nsu.buzyurkin;

public class HeapSort {
    private static void heapify(int a[], int n, int given_index){
        int max_index = given_index;
        int l = 2 * max_index + 1;
        int r = 2 * max_index + 2;

        if (l < n && a[l] > a[max_index])
            max_index = l;

        if (r < n && a[r] > a[max_index])
            max_index = r;

        if (max_index != given_index) {
            int helper = a[given_index];
            a[given_index] = a[max_index];
            a[max_index] = helper;

            heapify(a, n, max_index);
        }
    }

    public static void sort(int a[]){
        int n = a.length;

        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(a, n, i);

        for (int i = n - 1; i >= 0; i--){
            int helper = a[0];
            a[0] = a[i];
            a[i] = helper;

            heapify(a, i, 0);
        }
    }
}
