package com.example.FoodDeliveryBackend.domain.accountCommands;

import com.example.FoodDeliveryBackend.domain.enums.Roles;
import com.example.FoodDeliveryBackend.domain.enums.UserStatus;
import com.example.FoodDeliveryBackend.domain.exception.StructuredError;
import com.example.FoodDeliveryBackend.domain.model.AccountDomain;
import com.example.FoodDeliveryBackend.domain.port.out.AccountRepositoryPort;
import com.example.FoodDeliveryBackend.domain.port.out.KeycloakRegistrationPort;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
public class AddAccountCommand {


    private final AccountRepositoryPort accountRepositoryPort;
    private final KeycloakRegistrationPort keycloakRegistrationPort;

    public Either<StructuredError, SaveAccountOutput> execute(SaveAccountInput input){
        return keycloakRegistrationPort.registerCustomer(input)
                .flatMap(ignored ->
                        accountRepositoryPort.save(input.toParams())
                                .map(SaveAccountOutput::of)
                );
    }


    @Value
    public static class SaveAccountInput{

        String username;
        String firstName;
        String lastName;
        String phone;
        String email;
        String password;
        Roles role;

        private AccountDomain toParams(){
            return new AccountDomain(UUID.randomUUID(),username,role,firstName,lastName,phone,email,password, UserStatus.ONLINE, Instant.now());
        }

    }

    @Value
    public static class SaveAccountOutput{

        UUID id;
        String username;
        String firstName;
        String lastName;
        String phone;
        String email;
        Roles role;

        private static SaveAccountOutput of(AccountDomain domain){
            return new SaveAccountOutput(domain.getId(),domain.getUsername(), domain.getFirstName(),domain.getLastName(),
                    domain.getPhone(), domain.getEmail(), domain.getRole());
        }

    }

}
