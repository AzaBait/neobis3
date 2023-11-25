package com.example.neobis.service.impl;

import com.example.neobis.dto.SaveUserDto;
import com.example.neobis.entity.Role;
import com.example.neobis.entity.User;
import com.example.neobis.repository.UserRepo;
import com.example.neobis.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder encoder;

    public Optional<User> findUserByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }

    @Override
    public ResponseEntity<User> save(User user) {
        Optional<User> userByEmail = userRepo.findUserByEmail(user.getEmail());
        if (userByEmail.isPresent()) {
            throw new IllegalStateException("This email " + user.getEmail() + " is already exists!");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        User savedUser = userRepo.save(user);
        System.out.println("New user saved!");
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @Override
    public Optional<User> getById(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new IllegalStateException("User with id " + id + " does not exist!"));
        return Optional.ofNullable(user);
    }

    @Override
    public ResponseEntity<User> update(Long userId, SaveUserDto updatedUser) {
        User userInDB = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with id " + userId + " does not exist!"));
        userInDB.setName(updatedUser.getName());
        userInDB.setSurname(updatedUser.getSurname());
        userInDB.setEmail(updatedUser.getEmail());
        userInDB.setPhone(updatedUser.getPhone());
        userInDB.setPassword(encoder.encode(updatedUser.getPassword()));
        userInDB.setRoles(updatedUser.getRoles());

        userRepo.save(userInDB);
        System.out.println("User with id " + userId + " updated!");
        return ResponseEntity.ok(userInDB);
    }

    @Override
    public ResponseEntity<String> deleteUser(Long id) {
        if (userRepo.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id " + id + " does not exist!");
        }
        userRepo.deleteById(id);
        return ResponseEntity.ok("User with id " + id + " is deleted!");
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();

    }


}
