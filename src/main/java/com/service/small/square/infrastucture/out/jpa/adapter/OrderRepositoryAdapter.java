package com.service.small.square.infrastucture.out.jpa.adapter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.service.small.square.application.dto.dish.DishDto;
import com.service.small.square.application.mapper.OrderDishListMapper;
import com.service.small.square.domain.model.order.Order;
import com.service.small.square.domain.model.order.OrderDishList;
import com.service.small.square.domain.model.order.OrderStatus;
import com.service.small.square.domain.spi.IOrderPersistencePort;
import com.service.small.square.infrastucture.out.jpa.entity.OrderDishEntity;
import com.service.small.square.infrastucture.out.jpa.entity.OrderEntity;
import com.service.small.square.infrastucture.out.jpa.mapper.OrderEntityMapper;
import com.service.small.square.infrastucture.out.jpa.repository.DishRepository;
import com.service.small.square.infrastucture.out.jpa.repository.OrderDishRepository;
import com.service.small.square.infrastucture.out.jpa.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements IOrderPersistencePort {

    private final OrderRepository orderRepository;
    private final OrderEntityMapper orderEntityMapper;
    private final OrderDishListMapper orderDishListMapper;
    private final OrderDishRepository orderDishRepository;
    private final DishRepository dishRepository;

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
                    // Obtener los platos asociados a esta orden
                    List<OrderDishEntity> orderDishEntities = orderDishRepository.findByOrderId(orderEntity.getId());

                    // Mapear la información de los platos
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

                    // Mapear la orden y agregar la lista de platos
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
}

