package ru.srfholding.exception;

public class InvalidChangeStatusException extends RuntimeException {
    public InvalidChangeStatusException(String message) {
        super(message);
    }
}
