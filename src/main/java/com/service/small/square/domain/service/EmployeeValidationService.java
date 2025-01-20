package com.service.small.square.domain.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.service.small.square.domain.model.EmployeeRestaurant;
import com.service.small.square.infrastucture.exception.InvalidOrderException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EmployeeValidationService {

    private final WebClient employeRestaurantWebClient;


    public boolean validateEmployee(Long employeeId, Long restaurantId, String token) {
        try {
            EmployeeRestaurant employeeRestaurant = employeRestaurantWebClient.get()
                .uri("/{employeeId}", employeeId)
                .headers(headers -> headers.setBearerAuth(token))
                .retrieve()
                .onStatus(status -> status == HttpStatus.BAD_REQUEST, 
                    clientResponse -> Mono.error(new InvalidOrderException("Error al obtener la consulta")))
                .bodyToMono(EmployeeRestaurant.class)
                .block();

            return employeeId.equals(employeeRestaurant.getEmplooyeId()) && restaurantId.equals(employeeRestaurant.getRestaurantId());
        } catch (InvalidOrderException e) {
            throw new InvalidOrderException("No perteneces a este restaurante, no puedes realizar esta accion");
        }
    }
}
