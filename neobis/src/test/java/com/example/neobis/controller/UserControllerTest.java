package com.example.neobis.controller;

import com.example.neobis.entity.Role;
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

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class UserControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @WithMockUser(username = "authenticatedUser")
    @Test
    void getAll() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/user/list").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN", "MANAGER"})
    @Test
    void saveUser() throws Exception {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(3L, "USER"));
        User user = new User(
                0L, "taza", "tazaev", "taza@gmail.com", "+996755879654", "aza", roles
        );
        String jsonRequest = objectMapper.writeValueAsString(user);
        MvcResult mvcResult = mockMvc.perform(post("/api/user/save")
                .content(jsonRequest).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertEquals(201, mvcResult.getResponse().getStatus());
    }

    @WithMockUser(username = "authenticatedUser", roles = {"ADMIN"})
    @Test
    void getUserById() throws Exception {
        Long userId = 2L;
        MvcResult result = mockMvc.perform(get("/api/user/{id}", userId))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN", "MANAGER"})
    @Test
    void update() throws Exception {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(3L, "USER"));
        User user = new User(
                3L, "Ulan", "gafarov", "gaf@mail.com", "+99654879632", "ulan", roles
        );
        String jsonRequest = objectMapper.writeValueAsString(user);
        MvcResult result = mockMvc.perform(put("/api/user/{id}", user.getId())
                        .content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());

    }

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    void deleteUserById() throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/user/3"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
    }
}