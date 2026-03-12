package com.example.FoodDeliveryBackend.domain.service;


import com.example.FoodDeliveryBackend.domain.port.out.UserRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthService {
    private UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
}
