package com.service.small.square.application.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.service.small.square.application.dto.order.efficiency.EmployeeEfficiencyDto;
import com.service.small.square.domain.model.order.efficiency.EmployeeEfficiency;

@Mapper(componentModel = "spring")
public interface EmployeeEfficiencyMapper {
    EmployeeEfficiencyDto toDto(EmployeeEfficiency employeeEfficiency);
    List<EmployeeEfficiencyDto> toDtoList(List<EmployeeEfficiency> employeeEfficiencies);
}