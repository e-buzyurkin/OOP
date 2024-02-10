package ru.nsu.buzyurkin;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * ThreadsTest is a test class for evaluating the performance of different threading approaches
 * in the compound number checking operations.
 */
public class ThreadsTest {
    static List<Integer> testingList = new ArrayList<>();

    /**
     * Initializes the test list with integers from the input file.
     */
    @BeforeAll
    static void initList() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("./src/test/resources/input.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNextInt()) {
            int num = scanner.nextInt();
            testingList.add(num);
        }

    }

    /**
     * Tests the sequential compound number checking operation.
     */
    @Test
    void testSequential() {
        long start = System.currentTimeMillis();

        boolean res = SequentialRunner.anyCompound(testingList);
        assertFalse(res);

        long end = System.currentTimeMillis();
        System.out.print("Sequential: " + (double) ((end - start)) / 1000);
    }

    /**
     * Tests the parallel stream-based compound number checking operation.
     */
    @Test
    void testParallelStream() {
        long start = System.currentTimeMillis();

        boolean res = ParallelStreamRunner.anyCompound(testingList);
        assertFalse(res);

        long end = System.currentTimeMillis();
        System.out.print("ParallelStream: " + (double) (end - start));
    }

    /**
     * Tests the compound number checking operation using a single thread.
     *
     * @throws InterruptedException If the thread is interrupted during execution.
     */
    @Test
    void testMultiThreading1() throws InterruptedException {
        long start = System.currentTimeMillis();

        boolean res = MultiThreadedRunner.anyCompound(testingList, 1);
        assertFalse(res);

        long end = System.currentTimeMillis();
        System.out.print("1 Thread: " + (double) (end - start));
    }

    /**
     * Tests the compound number checking operation using two threads.
     *
     * @throws InterruptedException If the thread is interrupted during execution.
     */
    @Test
    void testMultiThreading2() throws InterruptedException {
        long start = System.currentTimeMillis();

        boolean res = MultiThreadedRunner.anyCompound(testingList, 2);
        assertFalse(res);

        long end = System.currentTimeMillis();
        System.out.print("2 Threads: " + (double) (end - start));
    }

    /**
     * Tests the compound number checking operation using four threads.
     *
     * @throws InterruptedException If the thread is interrupted during execution.
     */
    @Test
    void testMultiThreading4() throws InterruptedException {
        long start = System.currentTimeMillis();

        boolean res = MultiThreadedRunner.anyCompound(testingList, 4);
        assertFalse(res);

        long end = System.currentTimeMillis();
        System.out.print("4 Threads: " + (double) (end - start));
    }

    /**
     * Tests the compound number checking operation using six threads.
     *
     * @throws InterruptedException If the thread is interrupted during execution.
     */
    @Test
    void testMultiThreading6() throws InterruptedException {
        long start = System.currentTimeMillis();

        boolean res = MultiThreadedRunner.anyCompound(testingList, 6);
        assertFalse(res);

        long end = System.currentTimeMillis();
        System.out.print("6 Threads: " + (double) (end - start));
    }

    /**
     * Tests the compound number checking operation using eight threads.
     *
     * @throws InterruptedException If the thread is interrupted during execution.
     */
    @Test
    void testMultiThreading8() throws InterruptedException {
        long start = System.currentTimeMillis();

        boolean res = MultiThreadedRunner.anyCompound(testingList, 8);
        assertFalse(res);

        long end = System.currentTimeMillis();
        System.out.print("8 Threads: " + (double) (end - start));
    }
}
