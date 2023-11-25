package com.example.neobis.mapper;

import com.example.neobis.dto.OrderDetailsDto;
import com.example.neobis.entity.OrderDetails;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderDetailsMapper {

    OrderDetailsMapper INSTANCE = Mappers.getMapper(OrderDetailsMapper.class);

    OrderDetailsDto entityToDto(OrderDetails orderDetails);

    OrderDetails dtoToEntity(OrderDetailsDto orderDetailsDto);

    List<OrderDetailsDto> entitiesToDtos(List<OrderDetails> orderDetails);
}
