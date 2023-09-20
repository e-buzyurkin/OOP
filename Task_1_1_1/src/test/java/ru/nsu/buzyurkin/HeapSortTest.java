package ru.nsu.buzyurkin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HeapSortTest {

    @Test
    void test_1() {
        int[] a = {4, 5, 2, 1, 5, 5, 6, 100};
        int[] correct = {1, 2, 4, 5, 5, 5, 6, 100};

        HeapSort.sort(a);
        for (int i = 0; i < a.length; i++) {
            if (a[i] != correct[i]) {
                fail();
            }
        }

    }

    @Test
    void test_2() {
        int[] a = new int [1000];
        for (int i = 0; i < 1000; i++) {
            a[i] = (int) (Math.random() * 100000);
        }

        HeapSort.sort(a);

        for (int i = 0; i < 1000 - 1; i++) {
            if (a[i] > a[i + 1]) {
                fail();
            }
        }
    }

    @Test
    void test_3() {
        int[] a = {};
        HeapSort.sort(a);

        if (a.length > 0) {
            fail();
        }
    }

    @Test
    void test_4() {
        int[] a = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        HeapSort.sort(a);

        for (int i = 0; i < 10 - 1; i++) {
            if (a[i] > a[i + 1]) {
                fail();
            }
        }
    }

    @Test
    void test_final() {
        int Max = 100000;
        int[] a = new int [Max];
        for (int i = 0; i < Max; i++) {
            a[i] = (int) (Math.random() * 1000000 * Math.pow(-1, i));
        }

        HeapSort.sort(a);

        for (int i = 0; i < Max - 1; i++) {
            if (a[i] > a[i + 1]) {
                fail();
            }
        }
    }
}
