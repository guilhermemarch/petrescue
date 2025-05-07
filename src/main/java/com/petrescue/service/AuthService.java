package com.petrescue.service;

import com.petrescue.dto.auth.AuthResponse;
import com.petrescue.dto.auth.LoginRequest;
import com.petrescue.dto.auth.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
} 