package com.service.small.square.infrastucture.input.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.small.square.application.dto.restaurant.RestaurantRequestDto;
import com.service.small.square.application.handler.CreateRestaurantHandler;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final CreateRestaurantHandler restaurantHandler;

    @PostMapping
    public ResponseEntity<String> createRestaurant(@Valid @RequestBody RestaurantRequestDto restaurantDto, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        restaurantHandler.saveRestaurant(restaurantDto, token);
        return ResponseEntity.status(HttpStatus.CREATED).body("Restaurante creado correctamente");
    }

}