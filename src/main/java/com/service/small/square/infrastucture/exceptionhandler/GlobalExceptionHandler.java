package com.service.small.square.infrastucture.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.service.small.square.infrastucture.exception.InvalidRestaurantException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRestaurantException.class)
    public ResponseEntity<String> handlerRestaurantExeption(InvalidRestaurantException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}

