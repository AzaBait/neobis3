package com.example.neobis.service;

import com.example.neobis.dto.JwtRequest;

public interface AuthenticationService {
    String authenticateAndGetToken(JwtRequest request) throws Exception;
}
