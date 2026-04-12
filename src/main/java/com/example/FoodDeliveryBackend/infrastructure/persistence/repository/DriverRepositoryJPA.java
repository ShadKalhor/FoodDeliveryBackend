package com.example.FoodDeliveryBackend.infrastructure.persistence.repository;

import com.example.FoodDeliveryBackend.infrastructure.persistence.entity.Driver;
import io.vavr.control.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DriverRepositoryJPA extends JpaRepository<Driver, UUID> {

    Option<Driver> findOptionById(UUID driverId);

}
