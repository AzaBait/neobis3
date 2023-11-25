package com.example.neobis.service;

import com.example.neobis.dto.SaveUserDto;
import com.example.neobis.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    ResponseEntity<User> save(User user);

    Optional<User> getById(Long id);

    ResponseEntity<User> update(Long id, SaveUserDto user);

    ResponseEntity<String> deleteUser(Long id);

    List<User> getAllUsers();
}
