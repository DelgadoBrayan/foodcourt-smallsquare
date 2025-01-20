package com.service.small.square.infrastucture.input.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.small.square.application.dto.restaurant.RestaurantRequestDto;
import com.service.small.square.application.dto.restaurant.RestaurantResponseList;
import com.service.small.square.application.handler.CreateRestaurantHandler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final CreateRestaurantHandler restaurantHandler;

    @Operation(summary = "Create a new restaurant", description = "Creates a new restaurant with the provided details.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Restaurant created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    public ResponseEntity<String> createRestaurant(
            @Valid @RequestBody RestaurantRequestDto restaurantDto, 
            @RequestHeader("Authorization") String authorizationHeader) {
        
        String token = authorizationHeader.replace("Bearer ", "");
        restaurantHandler.saveRestaurant(restaurantDto, token);
        return ResponseEntity.status(HttpStatus.CREATED).body("Restaurante creado correctamente");
    }

    @Operation(summary = "Get restaurant by ID", description = "Retrieve a restaurant by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Restaurant found and returned successfully"),
        @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantRequestDto> getRestaurantById(@PathVariable Long restaurantId) {
        RestaurantRequestDto restaurantResponseList = restaurantHandler.findRestaurantById(restaurantId);
        if (restaurantResponseList != null) {
            return ResponseEntity.ok(restaurantResponseList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get a list of restaurants", description = "Retrieve a paginated list of restaurants, ordered by a certain criterion.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of restaurants retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
    })
    @GetMapping
    public ResponseEntity<List<RestaurantResponseList>> getRestaurants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        List<RestaurantResponseList> restaurants = restaurantHandler.getRestaurantsOrderedAndPaginated(page, size);
        return ResponseEntity.ok(restaurants);
    }
}
