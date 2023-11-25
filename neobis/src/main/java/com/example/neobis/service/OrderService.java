package com.example.neobis.service;

import com.example.neobis.dto.OrderDto;
import com.example.neobis.entity.Order;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    ResponseEntity<Order> save(Order order);

    Optional<Order> getById(Long id);

    ResponseEntity<Order> update(Long id, OrderDto orderDto);

    ResponseEntity<String> deleteOrder(Long id);

    List<Order> getAllOrders();
}
