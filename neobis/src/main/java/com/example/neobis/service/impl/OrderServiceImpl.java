package com.example.neobis.service.impl;

import com.example.neobis.dto.OrderDto;
import com.example.neobis.entity.Order;
import com.example.neobis.repository.OrderRepo;
import com.example.neobis.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;

    @Override
    public ResponseEntity<Order> save(Order order) {
        orderRepo.save(order);
        System.out.println("New order saved!");
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @Override
    public Optional<Order> getById(Long id) {
        Order order = orderRepo.findById(id).orElseThrow(() -> new IllegalStateException("Order with id " + id + " does not exist!"));
        return Optional.ofNullable(order);
    }

    @Override
    public ResponseEntity<Order> update(Long ordeIid, OrderDto updatedOrder) {
        Order orderInDB = orderRepo.findById(ordeIid)
                .orElseThrow(() -> new IllegalStateException("Car with id " + ordeIid + " does not exist!"));
        orderInDB.setUserId(updatedOrder.getUserId());
        orderInDB.setOrderDate(updatedOrder.getOrderDate());
        orderRepo.save(orderInDB);
        System.out.println("Car with id " + ordeIid + " updated!");
        return ResponseEntity.ok(orderInDB);
    }

    @Override
    public ResponseEntity<String> deleteOrder(Long id) {
        if (!orderRepo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with id " + id + " does not exist!");
        }
        orderRepo.deleteById(id);
        return ResponseEntity.ok("Order with id " + id + " is deleted!");
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }
}
