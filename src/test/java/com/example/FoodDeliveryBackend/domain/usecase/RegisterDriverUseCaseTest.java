package com.example.FoodDeliveryBackend.domain.usecase;

import com.example.FoodDeliveryBackend.domain.accountCommands.AddAccountCommand;
import com.example.FoodDeliveryBackend.domain.driverCommands.AddDriverCommand;
import com.example.FoodDeliveryBackend.domain.enums.Roles;
import com.example.FoodDeliveryBackend.domain.enums.VehicleType;
import com.example.FoodDeliveryBackend.domain.exception.StructuredError;
import com.example.FoodDeliveryBackend.domain.usecase.driver.RegisterDriverUseCase;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterDriverUseCaseTest {

    @Mock
    private AddAccountCommand addAccountCommand;

    @Mock
    private AddDriverCommand addDriverCommand;

    private RegisterDriverUseCase registerDriverUseCase;

    @BeforeEach
    void setUp() {
        registerDriverUseCase = new RegisterDriverUseCase(addAccountCommand, addDriverCommand);
    }

    @Test
    void shouldRegisterDriverSuccessfully() {
        // given
        UUID accountId = UUID.randomUUID();
        UUID driverId = UUID.randomUUID();

        RegisterDriverUseCase.RegisterDriverInput input =
                new RegisterDriverUseCase.RegisterDriverInput(
                        "JohnDoe",
                        "John","Doe",
                        "07712345678",
                        "john@example.com",
                        "password123",
                        VehicleType.CAR
                );

        AddAccountCommand.Output accountOutput =
                new AddAccountCommand.Output(
                        accountId,"JohnDoe",
                        "John","Doe",
                        "07712345678",
                        "john@example.com", Roles.DRIVER
                );

        AddDriverCommand.Output driverOutput =
                new AddDriverCommand.Output(
                        driverId,
                        accountId,
                        VehicleType.CAR,
                        BigDecimal.ZERO
                );

        when(addAccountCommand.execute(any(AddAccountCommand.Input.class)))
                .thenReturn(Either.right(accountOutput));

        when(addDriverCommand.execute(any(AddDriverCommand.Input.class)))
                .thenReturn(Either.right(driverOutput));

        // when
        Either<StructuredError, RegisterDriverUseCase.RegisterDriverOutput> result =
                registerDriverUseCase.execute(input);

        // then
        assertTrue(result.isRight());

        RegisterDriverUseCase.RegisterDriverOutput output = result.get();
        assertEquals(accountId, output.getAccountId());
        assertEquals(driverId, output.getDriverId());
        assertEquals("John", output.getFirstName());
        assertEquals("Doe", output.getLastName());
        assertEquals("john@example.com", output.getEmail());
        assertEquals("07712345678", output.getPhone());
        assertEquals(VehicleType.CAR, output.getVehicleType());

        ArgumentCaptor<AddAccountCommand.Input> accountCaptor =
                ArgumentCaptor.forClass(AddAccountCommand.Input.class);
        verify(addAccountCommand).execute(accountCaptor.capture());

        AddAccountCommand.Input capturedAccountInput = accountCaptor.getValue();
        assertEquals("John", capturedAccountInput.getFirstName());
        assertEquals("Doe", capturedAccountInput.getLastName());
        assertEquals("07712345678", capturedAccountInput.getPhone());
        assertEquals("john@example.com", capturedAccountInput.getEmail());
        assertEquals("password123", capturedAccountInput.getPassword());

        ArgumentCaptor<AddDriverCommand.Input> driverCaptor =
                ArgumentCaptor.forClass(AddDriverCommand.Input.class);
        verify(addDriverCommand).execute(driverCaptor.capture());

        AddDriverCommand.Input capturedDriverInput = driverCaptor.getValue();
        assertEquals(accountId, capturedDriverInput.getAccountId());
        assertEquals(VehicleType.CAR, capturedDriverInput.getVehicleType());
    }

    @Test
    void shouldReturnLeftWhenAddAccountFails() {
        // given
        RegisterDriverUseCase.RegisterDriverInput input =
                new RegisterDriverUseCase.RegisterDriverInput(
                        "JohnDoe",
                        "John","Doe",
                        "07712345678",
                        "john@example.com",
                        "password123",
                        VehicleType.BIKE
                );

        StructuredError error = mock(StructuredError.class);

        when(addAccountCommand.execute(any(AddAccountCommand.Input.class)))
                .thenReturn(Either.left(error));

        // when
        Either<StructuredError, RegisterDriverUseCase.RegisterDriverOutput> result =
                registerDriverUseCase.execute(input);

        // then
        assertTrue(result.isLeft());
        assertSame(error, result.getLeft());

        verify(addAccountCommand).execute(any(AddAccountCommand.Input.class));
        verifyNoInteractions(addDriverCommand);
    }

    @Test
    void shouldReturnLeftWhenAddDriverFails() {
        // given
        UUID accountId = UUID.randomUUID();

        RegisterDriverUseCase.RegisterDriverInput input =
                new RegisterDriverUseCase.RegisterDriverInput(
                        "JohnDoe",
                        "John","Doe",
                        "07712345678",
                        "john@example.com",
                        "password123",
                        VehicleType.BIKE
                );

        AddAccountCommand.Output accountOutput =
                new AddAccountCommand.Output(
                        accountId,
                        "JohnDoe",
                        "John","Doe",
                        "07712345678",
                        "john@example.com",
                        Roles.DRIVER
                );

        StructuredError error = mock(StructuredError.class);

        when(addAccountCommand.execute(any(AddAccountCommand.Input.class)))
                .thenReturn(Either.right(accountOutput));

        when(addDriverCommand.execute(any(AddDriverCommand.Input.class)))
                .thenReturn(Either.left(error));

        // when
        Either<StructuredError, RegisterDriverUseCase.RegisterDriverOutput> result =
                registerDriverUseCase.execute(input);

        // then
        assertTrue(result.isLeft());
        assertSame(error, result.getLeft());

        verify(addAccountCommand).execute(any(AddAccountCommand.Input.class));
        verify(addDriverCommand).execute(any(AddDriverCommand.Input.class));
    }
}