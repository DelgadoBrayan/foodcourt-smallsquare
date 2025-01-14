package com.service.small.square.infrastucture.exception;

public class InvalidRestaurantException extends RuntimeException {
    public InvalidRestaurantException(String messsage){
        super(messsage);
    }
}

