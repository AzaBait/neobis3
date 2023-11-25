package com.example.neobis.controller;

import com.example.neobis.dto.OrderDetailsDto;
import com.example.neobis.entity.OrderDetails;
import com.example.neobis.mapper.OrderDetailsMapper;
import com.example.neobis.service.OrderDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orderDetails")
public class OrderDetailsController {

    private final OrderDetailsService orderDetailsService;
    private final OrderDetailsMapper orderDetailsMapper;

    @PostMapping("/save")
    public ResponseEntity<OrderDetailsDto> saveOrderDetails(@Validated @RequestBody OrderDetailsDto orderDetailsDto) {
        OrderDetails orderDetails = orderDetailsService.save(orderDetailsMapper.dtoToEntity(orderDetailsDto)).getBody();
        OrderDetailsDto savedOrderDetailsDto = orderDetailsMapper.entityToDto(orderDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrderDetailsDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailsDto> getOrderDetailsById(@PathVariable Long id) {
        return orderDetailsService.getById(id)
                .map(orderDetails -> ResponseEntity.ok(orderDetailsMapper.entityToDto(orderDetails)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailsDto> update(@Validated @PathVariable Long id, @RequestBody OrderDetailsDto orderDetailsDto) {
        OrderDetails updatedOrderDetails = orderDetailsService.update(id, orderDetailsDto).getBody();
        if (updatedOrderDetails != null) {
            OrderDetailsDto orderDetailsDto1 = orderDetailsMapper.entityToDto(updatedOrderDetails);
            return ResponseEntity.ok(orderDetailsDto1);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderDetailsById(@PathVariable Long id) {
        return orderDetailsService.deleteOrderDetails(id);
    }

    @GetMapping("/list")
    public ResponseEntity<List<OrderDetailsDto>> getAll() {
        List<OrderDetailsDto> orderDetailsDtos = orderDetailsMapper.entitiesToDtos(orderDetailsService.getAllOrderDetails());
        return ResponseEntity.ok().body(orderDetailsDtos);
    }
}
