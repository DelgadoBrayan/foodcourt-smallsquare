package com.service.small.square.infrastucture.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.service.small.square.infrastucture.exception.InvalidDishException;
import com.service.small.square.infrastucture.exception.InvalidOrderException;
import com.service.small.square.infrastucture.exception.InvalidRestaurantException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRestaurantException.class)
    public ResponseEntity<String> handlerRestaurantExeption(InvalidRestaurantException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(InvalidDishException.class)
    public ResponseEntity<String> handlerDishException(InvalidDishException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(InvalidOrderException.class)
    public ResponseEntity<String> handlerOrderException(InvalidOrderException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}

