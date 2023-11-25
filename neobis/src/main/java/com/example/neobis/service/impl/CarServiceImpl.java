package com.example.neobis.service.impl;

import com.example.neobis.dto.CarDto;
import com.example.neobis.entity.Car;
import com.example.neobis.repository.CarRepo;
import com.example.neobis.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepo carRepo;

    @Override
    public ResponseEntity<Car> save(Car car) {
        carRepo.save(car);
        System.out.println("New car saved!");
        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }

    @Override
    public Optional<Car> getById(Long id) {
        Car car = carRepo.findById(id).orElseThrow(() -> new IllegalStateException("Car with id " + id + " does not exist!"));
        return Optional.ofNullable(car);
    }

    @Override
    public ResponseEntity<Car> update(Long carId, CarDto updatedCar) {
        Car carrInDB = carRepo.findById(carId)
                .orElseThrow(() -> new IllegalStateException("Car with id " + carId + " does not exist!"));
        carrInDB.setName(updatedCar.getName());
        carrInDB.setModel(updatedCar.getModel());
        carrInDB.setYear(updatedCar.getYear());
        carrInDB.setPrice(updatedCar.getPrice());

        carRepo.save(carrInDB);

        System.out.println("Car with id " + carId + " updated!");
        return ResponseEntity.ok(carrInDB);
    }

    @Override
    public ResponseEntity<String> deleteCar(Long id) {
        if (!carRepo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car with id " + id + " does not exist!");
        }
        carRepo.deleteById(id);
        return ResponseEntity.ok("Car with id " + id + " is deleted!");
    }

    @Override
    public List<Car> getAllCars() {
        return carRepo.findAll();
    }
}
