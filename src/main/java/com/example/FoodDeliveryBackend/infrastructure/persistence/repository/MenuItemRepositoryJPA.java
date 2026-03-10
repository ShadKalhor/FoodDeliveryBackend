package com.example.FoodDeliveryBackend.infrastructure.persistence.repository;

import com.example.FoodDeliveryBackend.infrastructure.persistence.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MenuItemRepositoryJPA extends JpaRepository<MenuItem, UUID> {
}
