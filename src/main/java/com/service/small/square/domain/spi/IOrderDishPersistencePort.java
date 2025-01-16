package com.service.small.square.domain.spi;

import com.service.small.square.domain.model.order.OrderDish;

public interface IOrderDishPersistencePort {
     OrderDish saveOrderDish(OrderDish orderDish);
}
