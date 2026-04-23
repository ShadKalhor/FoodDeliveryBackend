package com.example.FoodDeliveryBackend.infrastructure.persistence.repository;

import com.example.FoodDeliveryBackend.infrastructure.persistence.entity.Order;
import io.vavr.control.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepositoryJPA extends JpaRepository<Order, UUID> {

    Option<Order> findOptionById(UUID orderId);

}
