package com.example.FoodDeliveryBackend.infrastructure.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/register")
    public Void Register(){return null;}

    @PostMapping("/login")
    public Void Login(){return null;}

    @GetMapping("/me")
    public Void GetMe(){return null;}

}
