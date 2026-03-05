package com.example.FoodDeliveryBackend.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;


@Setter
@Getter
public class UserDomain {

    private UUID id;
    private Roles role;
    private String name;
    private String phone;
    private String email;
    private String password;
    private UserStatus status;
    private Instant createdAt;

}
