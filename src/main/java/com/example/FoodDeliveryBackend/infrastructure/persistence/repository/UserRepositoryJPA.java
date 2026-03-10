package com.example.FoodDeliveryBackend.infrastructure.persistence.repository;

import com.example.FoodDeliveryBackend.infrastructure.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepositoryJPA extends JpaRepository<User, UUID> {

}
