package com.example.neobis.controller;

import com.example.neobis.dto.SaveUserDto;
import com.example.neobis.dto.UserDto;
import com.example.neobis.entity.User;
import com.example.neobis.mapper.UserMapper;
import com.example.neobis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class WebController {

    private final UserService userService;
    private final UserMapper userMapper;

    @ModelAttribute("user")
    public SaveUserDto saveUserDto() {
        return new SaveUserDto();
    }

    @GetMapping("/registration")
    public String showRegistrationForm(){
        return "registration";
    }
    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user")SaveUserDto saveUserDto) {
        User savedUser = userService.save(userMapper.saveDtoToEntity(saveUserDto)).getBody();
        UserDto userDto = userMapper.entityToDto(savedUser);
        return "redirect:/registration?success";
    }
}
