package com.example.FoodDeliveryBackend.domain.driverCommands;

import com.example.FoodDeliveryBackend.domain.enums.VehicleType;
import com.example.FoodDeliveryBackend.domain.exception.StructuredError;
import com.example.FoodDeliveryBackend.domain.model.DriverDomain;
import com.example.FoodDeliveryBackend.domain.port.out.DriverRepositoryPort;
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
class AddDriverCommandTest {

    @Mock
    private DriverRepositoryPort driverRepositoryPort;

    private AddDriverCommand addDriverCommand;

    @BeforeEach
    void setUp() {
        addDriverCommand = new AddDriverCommand(driverRepositoryPort);
    }

    @Test
    void shouldAddDriverSuccessfully() {
        // given
        UUID accountId = UUID.randomUUID();

        AddDriverCommand.SaveDriverInput input =
                new AddDriverCommand.SaveDriverInput(accountId, VehicleType.CAR);

        DriverDomain savedDriver = new DriverDomain(
                UUID.randomUUID(),
                accountId,
                VehicleType.CAR,
                true,
                null,
                null,
                BigDecimal.ZERO
        );

        when(driverRepositoryPort.save(any(DriverDomain.class)))
                .thenReturn(Either.right(savedDriver));

        // when
        Either<StructuredError, AddDriverCommand.SaveDriverOutput> result =
                addDriverCommand.execute(input);

        // then
        assertTrue(result.isRight());

        AddDriverCommand.SaveDriverOutput output = result.get();

        assertEquals(savedDriver.getId(), output.getId());
        assertEquals(savedDriver.getUserId(), output.getAccountId());
        assertEquals(savedDriver.getVehicleType(), output.getVehicleType());
        assertEquals(savedDriver.getAvgRating(), output.getAvgRating());

        // verify interaction
        ArgumentCaptor<DriverDomain> captor = ArgumentCaptor.forClass(DriverDomain.class);
        verify(driverRepositoryPort).save(captor.capture());

        DriverDomain captured = captor.getValue();

        assertNotNull(captured.getId());
        assertEquals(accountId, captured.getUserId());
        assertEquals(VehicleType.CAR, captured.getVehicleType());
        assertTrue(captured.isOnline());
        assertEquals(BigDecimal.ZERO, captured.getAvgRating());
        assertNull(captured.getCurrentLocation());
        assertNull(captured.getLastLocationAt());
    }

    @Test
    void shouldReturnLeftWhenRepositoryFails() {
        // given
        UUID accountId = UUID.randomUUID();

        AddDriverCommand.SaveDriverInput input =
                new AddDriverCommand.SaveDriverInput(accountId, VehicleType.BIKE);

        StructuredError error = mock(StructuredError.class);

        when(driverRepositoryPort.save(any(DriverDomain.class)))
                .thenReturn(Either.left(error));

        // when
        Either<StructuredError, AddDriverCommand.SaveDriverOutput> result =
                addDriverCommand.execute(input);

        // then
        assertTrue(result.isLeft());
        assertEquals(error, result.getLeft());

        verify(driverRepositoryPort).save(any(DriverDomain.class));
    }
}