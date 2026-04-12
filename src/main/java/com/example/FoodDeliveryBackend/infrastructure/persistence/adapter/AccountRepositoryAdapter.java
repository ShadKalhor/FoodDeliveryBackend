package com.example.FoodDeliveryBackend.infrastructure.persistence.adapter;

import com.example.FoodDeliveryBackend.domain.exception.ErrorType;
import com.example.FoodDeliveryBackend.domain.exception.StructuredError;
import com.example.FoodDeliveryBackend.domain.model.AccountDomain;
import com.example.FoodDeliveryBackend.domain.port.out.AccountRepositoryPort;
import com.example.FoodDeliveryBackend.infrastructure.persistence.entity.Account;
import com.example.FoodDeliveryBackend.infrastructure.persistence.mapper.AccountMapper;
import com.example.FoodDeliveryBackend.infrastructure.persistence.repository.AccountRepositoryJPA;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class AccountRepositoryAdapter implements AccountRepositoryPort {

    private final AccountRepositoryJPA accountRepositoryJPA;
    private final AccountMapper accountMapper;

    @Override
    public Either<StructuredError, AccountDomain> save(AccountDomain domain) {

        Account entity = accountMapper.toEntity(domain);

        return Try.of(() -> accountRepositoryJPA.save(entity)).map(accountMapper::toDomain).toEither(new StructuredError("Error While Saving Account", ErrorType.DATABASE_ERROR));

    }

    @Override
    public Option<AccountDomain> findById(UUID accountId) {
        return accountRepositoryJPA.findOptionById(accountId).map(accountMapper::toDomain);
    }

    @Override
    public List<AccountDomain> findAll() {
        return accountRepositoryJPA.findAll().stream().map(accountMapper::toDomain).toList();
    }

    @Override
    public Option<AccountDomain> findByPhone(String phoneNumber) {
        return accountRepositoryJPA.findOptionByPhone(phoneNumber).map(accountMapper::toDomain);
    }

    @Override
    public Either<StructuredError, Void> deleteById(UUID accountId) {
        return Try.run(() -> accountRepositoryJPA.deleteById(accountId))
                .toEither(new StructuredError("Error While Deleting Account",
                        ErrorType.DATABASE_ERROR));
    }
}
