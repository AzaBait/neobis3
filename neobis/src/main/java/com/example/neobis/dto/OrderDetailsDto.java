package com.example.neobis.dto;

import com.example.neobis.entity.Car;
import com.example.neobis.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDto {

    private Long id;
    private Order orderId;
    private Car carId;
    private double totalPrice;
    private LocalDateTime orderDate;
}
