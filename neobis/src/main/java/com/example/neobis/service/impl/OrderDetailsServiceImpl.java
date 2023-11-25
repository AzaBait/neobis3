package com.example.neobis.service.impl;

import com.example.neobis.dto.OrderDetailsDto;
import com.example.neobis.entity.OrderDetails;
import com.example.neobis.repository.OrderDetailsRepo;
import com.example.neobis.service.OrderDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final OrderDetailsRepo orderDetailsRepo;

    @Override
    public ResponseEntity<OrderDetails> save(OrderDetails orderDetails) {
        orderDetailsRepo.save(orderDetails);
        System.out.println("New orderDetails saved!");
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDetails);
    }

    @Override
    public Optional<OrderDetails> getById(Long id) {
        OrderDetails orderDetails = orderDetailsRepo.findById(id).orElseThrow(() -> new IllegalStateException("OrderDetails with id " + id + " does not exist!"));
        return Optional.ofNullable(orderDetails);
    }

    @Override
    public ResponseEntity<OrderDetails> update(Long orderId, OrderDetailsDto updatedDetailsDto) {
        OrderDetails orderDetailsInDB = orderDetailsRepo.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Car with id " + orderId + " does not exist!"));
        orderDetailsInDB.setOrderId(updatedDetailsDto.getOrderId());
        orderDetailsInDB.setTotalPrice(updatedDetailsDto.getTotalPrice());
        orderDetailsInDB.setOrderDate(updatedDetailsDto.getOrderDate());
        orderDetailsRepo.save(orderDetailsInDB);
        System.out.println("Car with id " + orderId + " updated!");
        return ResponseEntity.ok(orderDetailsInDB);
    }

    @Override
    public ResponseEntity<String> deleteOrderDetails(Long id) {
        if (!orderDetailsRepo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with id " + id + " does not exist!");
        }
        orderDetailsRepo.deleteById(id);
        return ResponseEntity.ok("Order with id " + id + " is deleted!");
    }

    @Override
    public List<OrderDetails> getAllOrderDetails() {
        return orderDetailsRepo.findAll();
    }
}
