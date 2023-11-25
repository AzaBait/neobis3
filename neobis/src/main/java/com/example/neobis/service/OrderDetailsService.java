package com.example.neobis.service;

import com.example.neobis.dto.OrderDetailsDto;
import com.example.neobis.entity.OrderDetails;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface OrderDetailsService {

    ResponseEntity<OrderDetails> save(OrderDetails orderDetails);

    Optional<OrderDetails> getById(Long id);

    ResponseEntity<OrderDetails> update(Long id, OrderDetailsDto orderDetailsDto);

    ResponseEntity<String> deleteOrderDetails(Long id);

    List<OrderDetails> getAllOrderDetails();
}
