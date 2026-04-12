package com.example.FoodDeliveryBackend.domain.port.out;

import com.example.FoodDeliveryBackend.domain.exception.StructuredError;
import com.example.FoodDeliveryBackend.domain.model.AccountDomain;
import io.vavr.control.Either;
import io.vavr.control.Option;

import java.util.List;
import java.util.UUID;

public interface AccountRepositoryPort {

    Either<StructuredError, AccountDomain> save(AccountDomain domain);

    Option<AccountDomain> findById(UUID accountId);

    Option<AccountDomain> findByName(String name);

    List<AccountDomain> findAll();

    Option<AccountDomain> findByPhone(String phoneNumber);

    Either<StructuredError, Void> deleteById(UUID userId);




}
