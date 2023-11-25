package com.example.neobis.controller;

import com.example.neobis.dto.SaveUserDto;
import com.example.neobis.dto.UserDto;
import com.example.neobis.entity.User;
import com.example.neobis.mapper.UserMapper;
import com.example.neobis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/list")
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> userDtos = userMapper.entitiesToDtos(userService.getAllUsers());
        return ResponseEntity.ok().body(userDtos);
    }

    @PostMapping("/save")
    public ResponseEntity<UserDto> saveUser(@Validated @RequestBody SaveUserDto saveUserDto) {
        User savedUser = userService.save(userMapper.saveDtoToEntity(saveUserDto)).getBody();
        UserDto userDto = userMapper.entityToDto(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return userService.getById(id)
                .map(user -> ResponseEntity.ok(userMapper.entityToDto(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@Validated @PathVariable Long id, @RequestBody SaveUserDto userDto) {
        User updatedUser = userService.update(id, userDto).getBody();
        if (updatedUser != null) {
            UserDto userDto1 = userMapper.entityToDto(updatedUser);
            return ResponseEntity.ok(userDto1);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
};
