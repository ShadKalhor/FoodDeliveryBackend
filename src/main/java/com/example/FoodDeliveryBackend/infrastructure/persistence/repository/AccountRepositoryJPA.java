package com.example.FoodDeliveryBackend.infrastructure.persistence.repository;

import com.example.FoodDeliveryBackend.infrastructure.persistence.entity.Account;
import io.vavr.control.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface AccountRepositoryJPA extends JpaRepository<Account, UUID> {

    Option<Account> findByEmail(String email);

    boolean existsByEmail(String email);
}
