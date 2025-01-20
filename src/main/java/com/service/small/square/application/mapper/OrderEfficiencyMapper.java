package com.service.small.square.application.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.service.small.square.application.dto.order.efficiency.OrderEfficiencyDto;
import com.service.small.square.domain.model.order.efficiency.OrderEfficiency;

@Mapper(componentModel = "spring")
public interface OrderEfficiencyMapper {
    OrderEfficiencyDto toDto(OrderEfficiency orderEfficiency);
    List<OrderEfficiencyDto> toDtoList(List<OrderEfficiency> orderEfficiencies);
}

