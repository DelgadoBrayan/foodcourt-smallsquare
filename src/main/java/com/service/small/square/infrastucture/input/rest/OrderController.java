package com.service.small.square.infrastucture.input.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.small.square.application.dto.order.OrderDishListDto;
import com.service.small.square.application.dto.order.OrderRequest;
import com.service.small.square.application.dto.order.efficiency.EmployeeEfficiencyDto;
import com.service.small.square.application.dto.order.efficiency.OrderEfficiencyDto;
import com.service.small.square.application.handler.OrderHandler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderHandler orderHandler;
    private static final String BEARER_PREFIX = "Bearer ";

    @Operation(summary = "Create a new order", description = "Creates a new order with the provided details.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Order created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Void> createOrder(
            @Valid @RequestBody OrderRequest orderDto) {
        List<Long> listDishes = orderDto.getListDishes();
        orderHandler.createOrder(orderDto, listDishes);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get orders by status", description = "Retrieve a list of orders filtered by status and restaurant ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of orders retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    @GetMapping
    public ResponseEntity<List<OrderDishListDto>> getOrdersByStatus(
            @RequestParam String status,
            @RequestParam Long restaurantId,
            @RequestParam int page,
            @RequestParam int size
    ) {
        List<OrderDishListDto> orders = orderHandler.handleGetOrdersByStatus(status, restaurantId, page, size);
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Assign an employee to an order", description = "Assign an employee to a specific order by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Employee assigned successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid order ID or employee ID"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PatchMapping("/{orderId}/assign")
    public ResponseEntity<Void> assignEmployeeToOrder(
            @PathVariable Long orderId, 
            @RequestParam Long employeeId, 
            @RequestParam Long restaurantId, 
            @RequestHeader("Authorization") String token) {
        
        orderHandler.assignEmployeeToOrder(orderId, employeeId, restaurantId, token.replace(BEARER_PREFIX, ""));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Notify that the order is ready", description = "Notify when the order is ready for delivery.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order status updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid order ID or token"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PatchMapping("/{orderId}/ready")
    public ResponseEntity<Void> notifyOrderReady(
            @PathVariable Long orderId,
            @RequestHeader("Authorization") String token) {
        orderHandler.orderReady(orderId, token.replace(BEARER_PREFIX, ""));
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Deliver the order", description = "Complete the delivery of an order using the provided pin.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Order delivered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid order ID or pin"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PatchMapping("/{orderId}/deliver")
    public ResponseEntity<Void> deliverOrder(
            @PathVariable Long orderId,
            @RequestParam String pin,
            @RequestHeader("Authorization") String token) {
      
        orderHandler.deliverOrder(orderId, pin, token.replace(BEARER_PREFIX, ""));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cancel an order", description = "Cancel an existing order by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Order canceled successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid order ID")
    })
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderHandler.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get order efficiencies", description = "Retrieve the efficiency of orders based on time calculations.")
    @ApiResponse(responseCode = "200", description = "List of order efficiencies retrieved successfully")
    @GetMapping("/efficiency")
    public ResponseEntity<List<OrderEfficiencyDto>> getOrderEfficiencies() {
        return ResponseEntity.ok(orderHandler.getOrderEfficiencies());
    }

    @Operation(summary = "Get employee efficiencies", description = "Retrieve the average efficiency time per employee.")
    @ApiResponse(responseCode = "200", description = "List of employee efficiencies retrieved successfully")
    @GetMapping("/efficiency/employees")
    public ResponseEntity<List<EmployeeEfficiencyDto>> getEmployeeEfficiencies() {
        return ResponseEntity.ok(orderHandler.getEmployeeEfficiencies());
    }
}
