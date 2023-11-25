package com.example.neobis.controller;

import com.example.neobis.entity.Car;
import com.example.neobis.entity.Order;
import com.example.neobis.entity.OrderDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class OrderDetailsControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    @WithMockUser(username = "authenticatedUser")
    void saveOrderDetails() throws Exception {
        Order order = new Order();
        Car car = new Car();
        LocalDateTime localDateTime = LocalDateTime.of(2023, 11, 15, 0, 0);
        OrderDetails orderDetails = new OrderDetails(0L, order, car, 9000.0, localDateTime);
        String jsonRequest = mapper.writeValueAsString(orderDetails);
        MvcResult result = mockMvc.perform(post("/api/orderDetails/save")
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertEquals(201, result.getResponse().getStatus());
    }

    @Test
    @WithMockUser(username = "authenticatedUser")
    void getOrderDetailsById() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/orderDetails/1"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @WithMockUser(username = "authenticatedUser")
    void update() throws Exception {
        Order order = new Order();
        Car car = new Car();
        LocalDateTime localDateTime = LocalDateTime.of(2023, 11, 16, 01, 54);
        OrderDetails orderDetails = new OrderDetails(0L, order, car, 8700, localDateTime);
        String jsonRequest = mapper.writeValueAsString(orderDetails);
        MvcResult mvcResult = mockMvc.perform(put("/api/orderDetails/1")
                        .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    @WithMockUser("authenticatedUser")
    void deleteOrderDetailsById() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/orderDetails/1"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @WithMockUser("authenticatedUser")
    void getAll() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/orderDetails/list").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }
}