package com.service.small.square.infrastucture.exception;
public class InvalidDishException extends RuntimeException {
    public InvalidDishException(String message) {
        super(message);
    }
}