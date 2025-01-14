package com.service.small.square.infrastucture.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class InvalidRestaurantException extends RuntimeException {
    public InvalidRestaurantException(String messsage){
        super(messsage);
    }

    @ExceptionHandler(InvalidDishException.class)
    public ResponseEntity<String> handleInvalidDishException(InvalidDishException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}

