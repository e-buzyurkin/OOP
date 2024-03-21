package ru.nsu.buzyurkin.exceptions;
public class DeadClientException extends Exception {
    public DeadClientException() {
        super("Client has died");
    }
}
