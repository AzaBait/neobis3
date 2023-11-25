package com.example.neobis.controller;

import com.example.neobis.dto.JwtRequest;
import com.example.neobis.dto.JwtResponse;
import com.example.neobis.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest request) throws Exception {
        String token = authenticationService.authenticateAndGetToken(request);
        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.CREATED);
    }
}
