package com.example.neobis.controller;

import com.example.neobis.entity.Car;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class CarControllerTest {

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
    void saveCar() throws Exception {
        Car car = new Car(0L, "toyota", "camry", 2003, 9000.0);
        String jsonRequest = mapper.writeValueAsString(car);
        MvcResult result = mockMvc.perform(post("/api/car/save")
                        .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        assertEquals(201, result.getResponse().getStatus());
    }

    @Test
    @WithMockUser("authenticated")
    void getCarById() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/car/1"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @WithMockUser("authenticated")
    void update() throws Exception {
        Car car = new Car(0L, "volkswagen", "golf", 2002, 5000.0);
        String jsonRequest = mapper.writeValueAsString(car);
        MvcResult result = mockMvc.perform(put("/api/car/2")
                        .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @WithMockUser("authenticated")
    void deleteCarById() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/car/1"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    @WithMockUser("authenticated")
    void getAll() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/car/list"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }
}