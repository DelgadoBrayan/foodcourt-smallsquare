package com.service.small.square.infrastucture.out.jpa.adapter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.reactive.function.client.WebClient;

import com.service.small.square.application.dto.dish.DishDto;
import com.service.small.square.application.dto.users.UsersResponse;
import com.service.small.square.application.mapper.OrderDishListMapper;
import com.service.small.square.domain.model.order.Notification;
import com.service.small.square.domain.model.order.Order;
import com.service.small.square.domain.model.order.OrderDishList;
import com.service.small.square.domain.model.order.OrderStatus;
import com.service.small.square.domain.notification.helpers.PinGenerator;
import com.service.small.square.domain.spi.IOrderPersistencePort;
import com.service.small.square.infrastucture.exception.InvalidOrderException;
import com.service.small.square.infrastucture.out.jpa.entity.OrderDishEntity;
import com.service.small.square.infrastucture.out.jpa.entity.OrderEntity;
import com.service.small.square.infrastucture.out.jpa.mapper.OrderEntityMapper;
import com.service.small.square.infrastucture.out.jpa.repository.DishRepository;
import com.service.small.square.infrastucture.out.jpa.repository.OrderDishRepository;
import com.service.small.square.infrastucture.out.jpa.repository.OrderRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements IOrderPersistencePort {

    private final OrderRepository orderRepository;
    private final OrderEntityMapper orderEntityMapper;
    private final OrderDishListMapper orderDishListMapper;
    private final OrderDishRepository orderDishRepository;
    private final DishRepository dishRepository;
    private final WebClient userWebClient;
    private final WebClient orderWebClient;


    @Override
    public boolean existsByClientIdAndStatus(Long clientId, List<String> statuses) {
        List<OrderStatus> statusList = statuses.stream()
                .map(OrderStatus::valueOf)
                .toList();
        return orderRepository.existsByClientIdAndStatus(clientId, statusList);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id)
                .map(orderEntityMapper::toDomain);
    }

   @Override
    public List<OrderDishList> findOrdersByStatusAndRestaurantId(String status, Long restaurantId, int page, int size) {
        OrderStatus orderStatus = OrderStatus.valueOf(status);
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderEntity> orderEntities = orderRepository.findByStatusAndRestaurantId(orderStatus, restaurantId, pageable);

        return orderEntities.getContent().stream()
                .map(orderEntity -> {
                    List<OrderDishEntity> orderDishEntities = orderDishRepository.findByOrderId(orderEntity.getId());

                    List<DishDto> dishes = orderDishEntities.stream()
                            .map(orderDishEntity -> dishRepository.findById(orderDishEntity.getDishId())
                                    .map(dishEntity -> new DishDto(
                                            dishEntity.getId(),
                                            dishEntity.getName(),
                                            dishEntity.getPrice(),
                                            dishEntity.getDescription(),
                                            dishEntity.getUrlImage(),
                                            dishEntity.getCategory(),
                                            dishEntity.getRestaurantId(),
                                            dishEntity.getActive()
                                    ))
                                    .orElse(null))
                            .filter(Objects::nonNull)
                            .toList();

                    OrderDishList orderDishList = orderDishListMapper.toDomain(orderEntity);
                    orderDishList.setListDishes(dishes);

                    return orderDishList;
                })
                .toList();
    }
    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = orderEntityMapper.toEntity(order);
        return orderEntityMapper.toDomain(orderRepository.save(orderEntity));
    }

    @Override
    public void assignEmployeeToOrder(Long orderId, Long employeeId) {
        Optional<OrderEntity> entityOrderOptional = orderRepository.findById(orderId);
        if (entityOrderOptional.isPresent()) {
            OrderEntity entityOrder = entityOrderOptional.get();
            entityOrder.setChefId(employeeId);
            entityOrder.setStatus(OrderStatus.IN_PROCESS);
            orderRepository.save(entityOrder);
        } else {
            throw new EntityNotFoundException("Order not found with id: " + orderId);
        }
    }

@Override
public void noticationOrderReady(Long orderId, String token) {
    Optional<OrderEntity> orderEntityOptional = orderRepository.findById(orderId);
    if (orderEntityOptional.isEmpty()) {
        throw new EntityNotFoundException("Order not found with id: " + orderId);
    }
    
    OrderEntity orderEntity = orderEntityOptional.get();
    if (!OrderStatus.READY.equals(orderEntity.getStatus())) {
        throw new InvalidOrderException("Order cannot be marked as ready as it is not in process.");
    }
    
    String pin = PinGenerator.generatePin();
    orderEntity.setStatus(OrderStatus.READY);
    orderEntity.setPin(pin);
    UsersResponse userResponse = userWebClient.get()
        .uri("/{userId}", orderEntity.getClientId())
        .headers(headers -> headers.setBearerAuth(token))
        .retrieve()
        .bodyToMono(UsersResponse.class)
        .block();
    
    if (userResponse == null || userResponse.getPhone() == null) {
        throw new InvalidOrderException("Client information not found.");
    }
    orderRepository.save(orderEntity);
    
    
    //Notification notification = new Notification(orderEntity.getId(), userResponse.getPhone(), pin);
    // orderWebClient.post()
    //     .uri("/notification")
    //     .body(Mono.just(notification), Notification.class)
    //     .retrieve()
    //     .toBodilessEntity()
    //     .block();
}

@Override
public void deliverOrder(Long orderId) {
    Optional<OrderEntity> orderEntityOptional = orderRepository.findById(orderId);
    if (orderEntityOptional.isEmpty()) {
        throw new EntityNotFoundException("Order not found with id: " + orderId);
    }

    OrderEntity order = orderEntityOptional.get();

    order.setStatus(OrderStatus.DELIVERED);
}

}

