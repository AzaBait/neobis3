package com.example.neobis.mapper;

import com.example.neobis.dto.OrderDto;
import com.example.neobis.entity.Order;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDto entityToDto(Order order);

    Order dtoToEntity(OrderDto orderDto);

    List<OrderDto> entitiesToDtos(List<Order> orders);
}
