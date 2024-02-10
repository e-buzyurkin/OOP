package ru.nsu.buzyurkin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * MultiThreadedRunner is a utility class for running a compound number check operation
 * on a list of integers using multiple threads.
 */
public class MultiThreadedRunner {

    /**
     * Checks if any number in the given list is compound using multiple threads.
     *
     * @param list          The list of integers to be checked for compound numbers.
     * @param threadsCount  The number of threads to be used for parallel processing.
     * @return              {@code true} if any compound number is found, {@code false} otherwise.
     * @throws InterruptedException If any of the threads is interrupted during execution.
     */
    public static boolean anyCompound(List<Integer> list,
                                      int threadsCount) throws InterruptedException {
        AtomicBoolean result = new AtomicBoolean(false);

        int listSplitSize = list.size() / threadsCount;
        int listRemaining = list.size() % threadsCount;

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < threadsCount; i++) {
            int start = i * listSplitSize;
            int end = start + listSplitSize;
            if (i == threadsCount - 1) {
                end += listRemaining;
            }

            List<Integer> subList = list.subList(start, end);

            Thread thread = new Thread (
                    () -> {
                        boolean threadResult;
                        threadResult = PrimeUtil.anyCompound(subList);
                        result.compareAndExchange(false, threadResult);
                    }
            );

            threads.add(thread);
        }

        threads.forEach(Thread::start);

        for (var thread : threads) {
            try {
                thread.join();
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return result.get();
    }
}
