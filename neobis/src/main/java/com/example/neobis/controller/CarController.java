package com.example.neobis.controller;

import com.example.neobis.dto.CarDto;
import com.example.neobis.entity.Car;
import com.example.neobis.mapper.CarMapper;
import com.example.neobis.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/car")
public class CarController {

    private final CarService carService;
    private final CarMapper carMapper;

    @PostMapping("/save")
    public ResponseEntity<CarDto> saveCar(@Validated @RequestBody CarDto carDto) {
        Car car = carService.save(carMapper.dtoToEntity(carDto)).getBody();
        CarDto savedCarDto = carMapper.entityToDto(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCarDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long id) {
        return carService.getById(id)
                .map(car -> ResponseEntity.ok(carMapper.entityToDto(car)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDto> update(@Validated @PathVariable Long id, @RequestBody CarDto carDto) {
        Car updatedCar = carService.update(id, carDto).getBody();
        if (updatedCar != null) {
            CarDto carDto1 = carMapper.entityToDto(updatedCar);
            return ResponseEntity.ok(carDto1);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCarById(@PathVariable Long id) {
        return carService.deleteCar(id);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CarDto>> getAll() {
        List<CarDto> carDtos = carMapper.entitiesToDtos(carService.getAllCars());
        return ResponseEntity.ok().body(carDtos);
    }
}
