package com.service.small.square.infrastucture.input.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.small.square.application.dto.dish.DishDto;
import com.service.small.square.application.dto.dish.UpdateDishActive;
import com.service.small.square.application.dto.dish.UpdateDishDto;
import com.service.small.square.application.handler.DishHandler;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/owner/dishes")
@RequiredArgsConstructor
public class DishController {

    private final DishHandler dishHandler;

    @PostMapping
    public ResponseEntity<Void> createDish(@Valid @RequestBody DishDto dishDto,  @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        dishHandler.createDish(dishDto, token);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDish(@PathVariable Long id, @Valid @RequestBody UpdateDishDto dishDto) {
        dishHandler.updateDish(id, dishDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<Void> updateDishAvailability( @RequestHeader("Authorization") String authorizationHeader, @PathVariable Long id, @RequestBody UpdateDishActive updateDishActive) {

        String token = authorizationHeader.replace("Bearer ", "");
        dishHandler.toggleDishAvailability(id, updateDishActive, token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
