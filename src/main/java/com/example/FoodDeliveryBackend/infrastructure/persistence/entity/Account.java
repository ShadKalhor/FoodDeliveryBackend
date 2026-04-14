package com.example.FoodDeliveryBackend.infrastructure.persistence.entity;

import com.example.FoodDeliveryBackend.domain.enums.Roles;
import com.example.FoodDeliveryBackend.domain.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Roles role;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
    }

}
