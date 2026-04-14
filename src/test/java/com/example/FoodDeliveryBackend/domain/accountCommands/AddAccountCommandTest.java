package com.example.FoodDeliveryBackend.domain.accountCommands;

import com.example.FoodDeliveryBackend.domain.enums.Roles;
import com.example.FoodDeliveryBackend.domain.enums.UserStatus;
import com.example.FoodDeliveryBackend.domain.exception.StructuredError;
import com.example.FoodDeliveryBackend.domain.model.AccountDomain;
import com.example.FoodDeliveryBackend.domain.port.out.AccountRepositoryPort;
import com.example.FoodDeliveryBackend.domain.port.out.KeycloakRegistrationPort;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddAccountCommandTest {

    @Mock
    private AccountRepositoryPort accountRepositoryPort;

    @Mock
    private KeycloakRegistrationPort keycloakRegistrationPort;

    private AddAccountCommand addAccountCommand;

    @BeforeEach
    void setUp() {
        addAccountCommand = new AddAccountCommand(accountRepositoryPort, keycloakRegistrationPort);
    }

    @Test
    void shouldAddAccountSuccessfully() {
        // given
        AddAccountCommand.SaveAccountInput input = new AddAccountCommand.SaveAccountInput(
                "shad",
                "Shad",
                "Ali",
                "07700000000",
                "shad@test.com",
                "password123",
                Roles.CUSTOMER
        );

        AccountDomain savedAccount = new AccountDomain(
                UUID.randomUUID(),
                input.getUsername(),
                input.getRole(),
                input.getFirstName(),
                input.getLastName(),
                input.getPhone(),
                input.getEmail(),
                input.getPassword(),
                UserStatus.ONLINE,
                Instant.now()
        );

        when(keycloakRegistrationPort.registerCustomer(input))
                .thenReturn(Either.right(null));

        when(accountRepositoryPort.save(any(AccountDomain.class)))
                .thenReturn(Either.right(savedAccount));

        // when
        Either<StructuredError, AddAccountCommand.SaveAccountOutput> result = addAccountCommand.execute(input);

        // then
        assertTrue(result.isRight());

        AddAccountCommand.SaveAccountOutput output = result.get();
        assertEquals(savedAccount.getId(), output.getId());
        assertEquals(savedAccount.getUsername(), output.getUsername());
        assertEquals(savedAccount.getFirstName(), output.getFirstName());
        assertEquals(savedAccount.getLastName(), output.getLastName());
        assertEquals(savedAccount.getPhone(), output.getPhone());
        assertEquals(savedAccount.getEmail(), output.getEmail());
        assertEquals(savedAccount.getRole(), output.getRole());

        verify(keycloakRegistrationPort).registerCustomer(input);

        ArgumentCaptor<AccountDomain> captor = ArgumentCaptor.forClass(AccountDomain.class);
        verify(accountRepositoryPort).save(captor.capture());

        AccountDomain captured = captor.getValue();
        assertNotNull(captured.getId());
        assertEquals(input.getUsername(), captured.getUsername());
        assertEquals(input.getFirstName(), captured.getFirstName());
        assertEquals(input.getLastName(), captured.getLastName());
        assertEquals(input.getPhone(), captured.getPhone());
        assertEquals(input.getEmail(), captured.getEmail());
        assertEquals(input.getPassword(), captured.getPassword());
        assertEquals(input.getRole(), captured.getRole());
        assertEquals(UserStatus.ONLINE, captured.getStatus());
        assertNotNull(captured.getCreatedAt());
    }

    @Test
    void shouldReturnLeftWhenKeycloakRegistrationFails() {
        // given
        AddAccountCommand.SaveAccountInput input = new AddAccountCommand.SaveAccountInput(
                "shad",
                "Shad",
                "Ali",
                "07700000000",
                "shad@test.com",
                "password123",
                Roles.CUSTOMER
        );

        StructuredError error = mock(StructuredError.class);

        when(keycloakRegistrationPort.registerCustomer(input))
                .thenReturn(Either.left(error));

        // when
        Either<StructuredError, AddAccountCommand.SaveAccountOutput> result = addAccountCommand.execute(input);

        // then
        assertTrue(result.isLeft());
        assertEquals(error, result.getLeft());

        verify(keycloakRegistrationPort).registerCustomer(input);
        verify(accountRepositoryPort, never()).save(any());
    }

    @Test
    void shouldReturnLeftWhenRepositorySaveFails() {
        // given
        AddAccountCommand.SaveAccountInput input = new AddAccountCommand.SaveAccountInput(
                "shad",
                "Shad",
                "Ali",
                "07700000000",
                "shad@test.com",
                "password123",
                Roles.CUSTOMER
        );

        StructuredError error = mock(StructuredError.class);

        when(keycloakRegistrationPort.registerCustomer(input))
                .thenReturn(Either.right(null));

        when(accountRepositoryPort.save(any(AccountDomain.class)))
                .thenReturn(Either.left(error));

        // when
        Either<StructuredError, AddAccountCommand.SaveAccountOutput> result = addAccountCommand.execute(input);

        // then
        assertTrue(result.isLeft());
        assertEquals(error, result.getLeft());

        verify(keycloakRegistrationPort).registerCustomer(input);
        verify(accountRepositoryPort).save(any(AccountDomain.class));
    }
}