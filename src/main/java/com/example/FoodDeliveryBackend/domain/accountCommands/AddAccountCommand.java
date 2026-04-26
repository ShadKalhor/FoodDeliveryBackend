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

    public Either<StructuredError, Output> execute(Input input){
        return keycloakRegistrationPort.registerCustomer(input)
                .flatMap(ignored ->
                        accountRepositoryPort.save(input.toParams())
                                .map(Output::of)
                );
    }


    @Value
    public static class Input{

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
    public static class Output{

        UUID id;
        String username;
        String firstName;
        String lastName;
        String phone;
        String email;
        Roles role;

        private static Output of(AccountDomain domain){
            return new Output(domain.getId(),domain.getUsername(), domain.getFirstName(),domain.getLastName(),
                    domain.getPhone(), domain.getEmail(), domain.getRole());
        }

    }

}
