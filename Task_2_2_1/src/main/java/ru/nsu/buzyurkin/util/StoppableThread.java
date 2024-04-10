package ru.nsu.buzyurkin.util;

/**
 * The StoppableThread class is an abstract class that extends the Thread class
 * and provides a mechanism to request stopping the thread's execution.
 */
public abstract class StoppableThread extends Thread {

    /**
     * Abstract method to request stopping the thread's execution.
     * Subclasses must implement this method to define how the thread should be stopped.
     */
    public abstract void requestStop();
}
