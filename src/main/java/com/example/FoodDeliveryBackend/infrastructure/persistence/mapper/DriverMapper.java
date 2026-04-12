package com.example.FoodDeliveryBackend.infrastructure.persistence.mapper;


import com.example.FoodDeliveryBackend.domain.model.DriverDomain;
import com.example.FoodDeliveryBackend.infrastructure.persistence.entity.Account;
import com.example.FoodDeliveryBackend.infrastructure.persistence.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.UUID;

public interface DriverMapper {

    @Mapping(target = "userId", source = "user")
    DriverDomain toDomain(Driver entity);

    @Mapping(target = "user", source = "userId")
    Driver toEntity(DriverDomain domain);


    default Account map(UUID userId) {
        if (userId == null) {
            return null;
        }
        Account account = new Account();
        account.setId(userId);
        return account;
    }
    default UUID map(Account account) {
        return account != null ? account.getId() : null;
    }
}
