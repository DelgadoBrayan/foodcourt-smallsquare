package com.service.small.square.infrastucture.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.service.small.square.domain.api.IRestaurantServicePort;
import com.service.small.square.domain.spi.IRestaurantPersistencePort;
import com.service.small.square.domain.usecase.CreateRestaurantUseCase;
import com.service.small.square.infrastucture.out.jpa.adapter.RestaurantRepositoryAdapter;
import com.service.small.square.infrastucture.out.jpa.mapper.RestaurantEntityMapper;
import com.service.small.square.infrastucture.out.jpa.repository.RestaurantJpaRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;
    private final WebClient webClient;

    @Bean
    IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantRepositoryAdapter(restaurantJpaRepository, restaurantEntityMapper);
    }

    @Bean
    IRestaurantServicePort restaurantServicePort() {
        return new CreateRestaurantUseCase(restaurantPersistencePort(), webClient);
    }

}
