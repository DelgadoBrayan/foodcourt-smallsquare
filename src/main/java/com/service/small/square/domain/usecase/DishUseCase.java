package com.service.small.square.domain.usecase;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.service.small.square.application.dto.validate.OwnerResponseDto;
import com.service.small.square.domain.api.IDishServicePort;
import com.service.small.square.domain.model.Restaurant;
import com.service.small.square.domain.model.dish.Dish;
import com.service.small.square.domain.spi.IDishPersistencePort;
import com.service.small.square.domain.spi.IRestaurantPersistencePort;
import com.service.small.square.infrastucture.exception.InvalidDishException;
import com.service.small.square.infrastucture.exception.InvalidRestaurantException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final WebClient webClient;

    @Override
    public void saveDish(Dish dish, String token) {

        Restaurant restaurant = restaurantPersistencePort.findRestaurantById(dish.getRestaurantId());

        validateOwner(restaurant.getOwnerId(), token);
        if (dish.getDishInfo().getPrice() <= 0) {
            throw new InvalidDishException("El precio debe ser mayor a 0");
        }
        if (dish.getRestaurantId()== null) {
            throw new InvalidDishException("El ID del restaurante no puede ser nulo");
        }
        dish.setActive(true);
        dishPersistencePort.saveDish(dish);
    }

    @Override
    public Dish findDishById(Long id) {
        return dishPersistencePort.findDishById(id);
    }


    @Override
    public void updateDish(Long id, Double price, String description) {
        Dish dish = dishPersistencePort.findDishById(id);
        if (dish == null) {
            throw new InvalidDishException("Plato no encontrado");
        }
        dish.getDishInfo().setPrice(price);
        dish.getDishInfo().setDescription(description);
        dishPersistencePort.saveDish(dish);
    }

    @Override
    public void toggleDishAvailability(Long id, boolean isAvailable, String token) {
        Dish dish = dishPersistencePort.findDishById(id);

        if (dish == null) {
            throw new InvalidDishException("Plato no encontrado");
        }
        
        Restaurant restaurant = restaurantPersistencePort.findRestaurantById(dish.getRestaurantId());

        validateOwner(restaurant.getOwnerId(), token);
        
        dish.setActive(isAvailable);
        dishPersistencePort.saveDish(dish);
    }

    @Override
    public List<Dish> listDishesByRestaurant(Long restaurantId, int page, int size, String category) {
        return dishPersistencePort.listDishesByRestaurant(restaurantId, page, size, category);
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
