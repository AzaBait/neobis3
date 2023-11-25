package com.example.neobis.service;

import com.example.neobis.dto.CarDto;
import com.example.neobis.entity.Car;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CarService {

    ResponseEntity<Car> save(Car car);

    Optional<Car> getById(Long id);

    ResponseEntity<Car> update(Long id, CarDto carDto);

    ResponseEntity<String> deleteCar(Long id);

    List<Car> getAllCars();
}
