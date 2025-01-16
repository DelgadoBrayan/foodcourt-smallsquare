package com.service.small.square.infrastucture.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.service.small.square.application.mapper.OrderDishListMapper;
import com.service.small.square.domain.api.IDishServicePort;
import com.service.small.square.domain.api.IOrderDishServicePort;
import com.service.small.square.domain.api.IOrderServicePort;
import com.service.small.square.domain.api.IRestaurantServicePort;
import com.service.small.square.domain.spi.IDishPersistencePort;
import com.service.small.square.domain.spi.IOrderDishPersistencePort;
import com.service.small.square.domain.spi.IOrderPersistencePort;
import com.service.small.square.domain.spi.IRestaurantPersistencePort;
import com.service.small.square.domain.usecase.CreateRestaurantUseCase;
import com.service.small.square.domain.usecase.DishUseCase;
import com.service.small.square.domain.usecase.OrderDishUseCase;
import com.service.small.square.domain.usecase.OrderUseCase;
import com.service.small.square.infrastucture.out.jpa.adapter.DishJpaAdapter;
import com.service.small.square.infrastucture.out.jpa.adapter.OrderDishAdapter;
import com.service.small.square.infrastucture.out.jpa.adapter.OrderRepositoryAdapter;
import com.service.small.square.infrastucture.out.jpa.adapter.RestaurantRepositoryAdapter;
import com.service.small.square.infrastucture.out.jpa.mapper.DishEntityMapper;
import com.service.small.square.infrastucture.out.jpa.mapper.OrderDishEntityMapper;
import com.service.small.square.infrastucture.out.jpa.mapper.OrderEntityMapper;
import com.service.small.square.infrastucture.out.jpa.mapper.RestaurantEntityMapper;
import com.service.small.square.infrastucture.out.jpa.repository.DishRepository;
import com.service.small.square.infrastucture.out.jpa.repository.OrderDishRepository;
import com.service.small.square.infrastucture.out.jpa.repository.OrderRepository;
import com.service.small.square.infrastucture.out.jpa.repository.RestaurantJpaRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;
    private final DishRepository dishRepository;
    private final DishEntityMapper dishEntityMapper;
    private final OrderRepository orderRepository;
    private final OrderEntityMapper orderEntityMapper;
    private final OrderDishRepository orderDishRepository;
    private final OrderDishEntityMapper orderDishEntityMapper;
    private final OrderDishListMapper orderDishListMapper;
    private final WebClient webClient;

    @Bean
    IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantRepositoryAdapter(restaurantJpaRepository, restaurantEntityMapper);
    }

    @Bean
    IRestaurantServicePort restaurantServicePort() {
        return new CreateRestaurantUseCase(restaurantPersistencePort(), webClient);
    }

        @Bean
    IDishPersistencePort dishPersistencePort() {
        return new DishJpaAdapter(dishRepository, dishEntityMapper);
    }

    @Bean
    IDishServicePort dishServicePort(){
        return new DishUseCase(dishPersistencePort(),restaurantPersistencePort(), webClient);
    }

    @Bean
    IOrderPersistencePort orderPersistencePort(){
        return new OrderRepositoryAdapter(orderRepository, 
                                        orderEntityMapper,
                                        orderDishListMapper,
                                        orderDishRepository,
                                        dishRepository );
    }

    @Bean
    IOrderServicePort orderServicePort(){
        return new OrderUseCase(orderPersistencePort(), orderDishPersistencePort());
    }

    @Bean
    IOrderDishPersistencePort orderDishPersistencePort(){
        return new OrderDishAdapter(orderDishRepository, orderDishEntityMapper);
    }

    @Bean
    IOrderDishServicePort orderDishServicePort(){
        return new OrderDishUseCase(orderDishPersistencePort());
    }

}
