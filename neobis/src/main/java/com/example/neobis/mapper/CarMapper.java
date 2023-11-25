package com.example.neobis.mapper;

import com.example.neobis.dto.CarDto;
import com.example.neobis.entity.Car;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarMapper {

    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    CarDto entityToDto(Car car);

    Car dtoToEntity(CarDto carDto);

    List<CarDto> entitiesToDtos(List<Car> cars);
}
