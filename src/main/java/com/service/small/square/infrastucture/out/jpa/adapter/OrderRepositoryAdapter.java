package com.service.small.square.infrastucture.out.jpa.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.service.small.square.domain.model.order.Order;
import com.service.small.square.domain.model.order.OrderStatus;
import com.service.small.square.domain.spi.IOrderPersistencePort;
import com.service.small.square.infrastucture.out.jpa.entity.OrderEntity;
import com.service.small.square.infrastucture.out.jpa.mapper.OrderEntityMapper;
import com.service.small.square.infrastucture.out.jpa.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements IOrderPersistencePort {
    private final OrderRepository orderRepository;
    private final OrderEntityMapper orderEntityMapper;

    @Override
    public boolean existsByClientIdAndStatus(Long clientId, List<String> statuses) {
        List<OrderStatus> statusList = statuses.stream().map(OrderStatus::valueOf).toList();
        return orderRepository.existsByClientIdAndStatus(clientId, statusList);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id).map(orderEntityMapper::toDomain);
    }

    @Override
    public List<Order> findByStatus(String status, Long restaurantId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<OrderEntity> orderEntities = orderRepository.findByStatusAndRestaurantId(OrderStatus.valueOf(status), restaurantId, pageable);
        return orderEntityMapper.toDomainList(orderEntities);
    }

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = orderEntityMapper.toEntity(order);
        return orderEntityMapper.toDomain(orderRepository.save(orderEntity));
    }
}

