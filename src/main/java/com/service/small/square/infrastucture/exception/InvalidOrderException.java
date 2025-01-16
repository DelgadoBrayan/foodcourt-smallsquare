package com.service.small.square.infrastucture.exception;

public class InvalidOrderException extends RuntimeException {

    public InvalidOrderException(String message){
        super(message);
    }
}
