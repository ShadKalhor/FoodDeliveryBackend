package com.example.FoodDeliveryBackend.infrastructure.persistence.mapper;


import com.example.FoodDeliveryBackend.domain.model.AccountDomain;
import com.example.FoodDeliveryBackend.infrastructure.persistence.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AccountMapper {

    AccountDomain toDomain(Account entity);

    Account toEntity(AccountDomain domain);


}
