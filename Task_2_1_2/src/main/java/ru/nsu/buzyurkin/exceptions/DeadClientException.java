package ru.nsu.buzyurkin.exceptions;

/**
 * An exception indicating that the client has died.
 */
public class DeadClientException extends Exception {
    /**
     * Constructs a DeadClientException with the default message.
     */
    public DeadClientException() {
        super("Client has died");
    }
}
