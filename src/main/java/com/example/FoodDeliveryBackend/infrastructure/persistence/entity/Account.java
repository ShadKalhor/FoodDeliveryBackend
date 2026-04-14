package com.example.FoodDeliveryBackend.infrastructure.persistence.entity;

import com.example.FoodDeliveryBackend.domain.enums.Roles;
import com.example.FoodDeliveryBackend.domain.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "account")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    private UUID id;

    @NotNull
    private String username;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Roles role;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String phone;
    @NotNull
    private String email;
    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
    }

}
