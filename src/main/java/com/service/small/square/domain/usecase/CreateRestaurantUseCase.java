package com.service.small.square.domain.usecase;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.service.small.square.application.dto.validate.OwnerResponseDto;
import com.service.small.square.domain.api.IRestaurantServicePort;
import com.service.small.square.domain.model.Restaurant;
import com.service.small.square.domain.spi.IRestaurantPersistencePort;
import com.service.small.square.infrastucture.exception.InvalidRestaurantException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreateRestaurantUseCase implements IRestaurantServicePort{
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final WebClient webClient;

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant,String token) {
        validateRestaurant(restaurant);
        validateOwner(restaurant.getOwnerId(), token);

        return restaurantPersistencePort.saveRestaurant(restaurant);
    }

    @Override
    public Restaurant findRestaurantById(Long restaurantId) {
        return restaurantPersistencePort.findRestaurantById(restaurantId);
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (restaurant.getName() == null || restaurant.getName().isEmpty()) {
            throw new InvalidRestaurantException("El nombre del restaurante no debe estar vacío o nulo");
        }
        if (restaurant.getNit() == null || restaurant.getNit().isEmpty()) {
            throw new InvalidRestaurantException("El NIT del restaurante no debe estar vacío o nulo");
        }
        if (restaurant.getAddress() == null || restaurant.getAddress().isEmpty()) {
            throw new InvalidRestaurantException("La dirección del restaurante no debe estar vacía o nula");
        }
        if (restaurant.getPhone() == null || restaurant.getPhone().isEmpty()) {
            throw new InvalidRestaurantException("El teléfono del restaurante no debe estar vacío o nulo");
        }
        if (restaurant.getUrlLogo() == null || restaurant.getUrlLogo().isEmpty()) {
            throw new InvalidRestaurantException("La URL del logo del restaurante no debe estar vacía o nula");
        }
        if (restaurant.getOwnerId() == null) {
            throw new InvalidRestaurantException("El ID del propietario no debe ser nulo");
        }
        if (!restaurant.getNit().matches("\\d+")) {
            throw new InvalidRestaurantException("El NIT debe contener únicamente caracteres numéricos");
        }
        if (!restaurant.getPhone().matches("\\+?\\d{1,13}")) {
            throw new InvalidRestaurantException("El teléfono debe ser numérico, con un máximo de 13 caracteres, y puede incluir '+'");
        }
        if (restaurant.getName().matches("\\d+")) {
            throw new InvalidRestaurantException("El nombre del restaurante no puede contener únicamente números");
        }
    }
    
private void validateOwner(Long ownerId, String token) {
    if (ownerId == null) {
        throw new InvalidRestaurantException("El ID del propietario no puede ser nulo");
    }

    OwnerResponseDto ownerResponse = webClient.get()
    .uri("/{idOwner}", ownerId)
    .headers(headers -> headers.setBearerAuth(token))
    .retrieve()
    .onStatus(status -> status == HttpStatus.BAD_REQUEST, clientResponse -> Mono.error(new InvalidRestaurantException("Error al obtener el propietario")))
    .bodyToMono(OwnerResponseDto.class)
    .block();

    if (ownerResponse == null || ownerResponse.getRoleId() != 1) {
        throw new InvalidRestaurantException("No puedes realizar esta acción por tu rol");
    }
}

}