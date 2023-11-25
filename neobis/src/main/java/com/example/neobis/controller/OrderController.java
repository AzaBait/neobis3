package com.example.neobis.controller;

import com.example.neobis.dto.OrderDto;
import com.example.neobis.entity.Order;
import com.example.neobis.mapper.OrderMapper;
import com.example.neobis.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping("/save")
    public ResponseEntity<OrderDto> saveOrder(@Validated @RequestBody OrderDto orderDto) {
        Order order = orderService.save(orderMapper.dtoToEntity(orderDto)).getBody();
        OrderDto savedOrderDto = orderMapper.entityToDto(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrderDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        return orderService.getById(id)
                .map(order -> ResponseEntity.ok(orderMapper.entityToDto(order)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> update(@Validated @PathVariable Long id, @RequestBody OrderDto orderDto) {
        Order updatedOrder = orderService.update(id, orderDto).getBody();
        if (updatedOrder != null) {
            OrderDto orderDto1 = orderMapper.entityToDto(updatedOrder);
            return ResponseEntity.ok(orderDto1);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderById(@PathVariable Long id) {
        return orderService.deleteOrder(id);
    }

    @GetMapping("/list")
    public ResponseEntity<List<OrderDto>> getAll() {
        List<OrderDto> orderDtos = orderMapper.entitiesToDtos(orderService.getAllOrders());
        return ResponseEntity.ok().body(orderDtos);
    }

}
