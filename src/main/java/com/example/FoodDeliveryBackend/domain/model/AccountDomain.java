package com.example.FoodDeliveryBackend.domain.model;

import com.example.FoodDeliveryBackend.domain.enums.Roles;
import com.example.FoodDeliveryBackend.domain.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;


@Setter
@Getter
@AllArgsConstructor
public class AccountDomain {

    private UUID id;
    private Roles role;
    private String name;
    private String phone;
    private String email;
    private String password;
    private UserStatus status;
    private Instant createdAt;

}
