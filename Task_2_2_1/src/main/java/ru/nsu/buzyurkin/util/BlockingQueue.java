package ru.nsu.buzyurkin.util;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * The BlockingQueue class implements a thread-safe blocking queue with a specified capacity.
 * It allows elements to be pushed into the queue and popped out of the queue.
 * If the queue is empty when trying to pop, the calling
 *      thread will block until an element becomes available.
 * If the queue is full when trying to push, the calling
 *      thread will block until space becomes available.
 *
 * @param <T> the type of elements held in this queue
 */
public class BlockingQueue<T> {
    private Queue<T> queue = new ArrayDeque<>();

    private int capacity;

    /**
     * Constructs a BlockingQueue with the specified capacity.
     *
     * @param capacity the capacity of the queue
     */
    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Retrieves and removes the head of the queue,
     *      waiting if necessary until an element becomes available.
     *
     * @return the head of the queue
     * @throws InterruptedException if the current thread is interrupted while waiting
     */
    public synchronized T pop() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }

        T value = queue.poll();
        notifyAll();
        return value;
    }

    /**
     * Inserts the specified element into the queue,
     *      waiting if necessary until space becomes available.
     *
     * @param value the element to add
     * @throws InterruptedException if the current thread is interrupted while waiting
     */
    public synchronized void push(T value) throws InterruptedException {
        while (queue.size() == capacity) {
            wait();
        }

        queue.add(value);
        notifyAll();
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue is empty, false otherwise
     */
    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
