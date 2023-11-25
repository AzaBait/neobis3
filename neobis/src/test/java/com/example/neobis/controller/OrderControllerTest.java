package com.example.neobis.controller;

import com.example.neobis.entity.Order;
import com.example.neobis.entity.User;
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
class OrderControllerTest {

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
    @WithMockUser("authenticated")
    void saveOrder() throws Exception {
        User user = new User();
        LocalDateTime localDateTime = LocalDateTime.of(2023, 11, 15, 0, 0);
        Order order = new Order(0L, user, localDateTime);
        String jsonRequest = mapper.writeValueAsString(order);
        MvcResult result = mockMvc.perform(post("/api/order/save")
                        .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        assertEquals(201, result.getResponse().getStatus());
    }

    @Test
    @WithMockUser("authenticated")
    void getOrderById() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/order/1"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @WithMockUser("authenticated")
    void update() throws Exception {
        User user = new User();
        LocalDateTime localDateTime = LocalDateTime.of(2023, 11, 15, 1, 33);
        Order order = new Order(0L, user, localDateTime);
        String jsonRequest = mapper.writeValueAsString(order);
        MvcResult result = mockMvc.perform(put("/api/order/1")
                        .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @WithMockUser("authenticated")
    @Test
    void deleteOrderById() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/order/1"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @WithMockUser("authenticated")
    void getAll() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/order/list"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }
}