package com.service.small.square.domain.api;

import com.service.small.square.domain.model.order.OrderDish;

public interface IOrderDishServicePort {
    OrderDish saveOrderDish(OrderDish orderDish);
}
