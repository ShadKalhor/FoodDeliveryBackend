package com.example.FoodDeliveryBackend.domain.customerCommands;

import com.example.FoodDeliveryBackend.domain.enums.OrderStatus;
import com.example.FoodDeliveryBackend.domain.enums.PaymentStatus;
import com.example.FoodDeliveryBackend.domain.exception.ErrorType;
import com.example.FoodDeliveryBackend.domain.exception.StructuredError;
import com.example.FoodDeliveryBackend.domain.model.OrderDomain;
import com.example.FoodDeliveryBackend.domain.model.OrderItemDomain;
import com.example.FoodDeliveryBackend.domain.model.Pricing;
import com.example.FoodDeliveryBackend.domain.model.Timestamps;
import com.example.FoodDeliveryBackend.domain.port.out.OrderRepositoryPort;
import com.example.FoodDeliveryBackend.domain.service.OrderPricingService;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class AddOrderCommand {

    private final OrderRepositoryPort orderRepositoryPort;
    private final AddOrderItemCommand addOrderItemCommand = new AddOrderItemCommand();
    private final OrderPricingService orderPricingService = new OrderPricingService();

    public Either<StructuredError, AddOrderOutput> execute(Input input) {

        return Try.of(() ->
                addItems(input.itemsInput)
                        .map(items -> {
                            OrderDomain orderDomain = input.toParams();
                            orderDomain.setPricing(orderPricingService.calculatePricing(items));
                            orderDomain.setItems(items);
                            return orderDomain;
                        })
                        .flatMap(orderRepositoryPort::save)
                        .map(AddOrderOutput::of)
        ).getOrElseGet(throwable ->
                Either.left(
                        new StructuredError(
                                "Unexpected Error While Adding Order",
                                ErrorType.SERVER_ERROR
                        )
                )
        );

    }

    private Either<StructuredError, List<OrderItemDomain>> addItems(List<AddOrderItemCommand.Input> itemInputs){

        List<OrderItemDomain> itemOutputs = new ArrayList<>();

        for (AddOrderItemCommand.Input item : itemInputs) {

            Either<StructuredError,OrderItemDomain> result =
                    addOrderItemCommand.execute(item);

            if (result.isRight())
                itemOutputs.add(result.get());
            else
                return Either.left(result.getLeft());

        }
        return Either.right(itemOutputs);
    }

    @Value
    public static class Input {

        @NotNull
        UUID customerId;
        @NotNull
        UUID restaurantId;
        @NotNull
        UUID deliveryAddressId;
        @NotNull@NotEmpty
        List<AddOrderItemCommand.Input> itemsInput;

        private OrderDomain toParams() {

            Timestamps timestamps = new Timestamps(Instant.now());
            return new OrderDomain(UUID.randomUUID(), customerId,restaurantId,
                    deliveryAddressId, OrderStatus.DRAFT, null,timestamps,
                    null, PaymentStatus.PENDING);
        }
    }

    @Value
    public static class AddOrderOutput {

        UUID id;
        UUID customerId;
        UUID restaurantId;
        UUID deliveryAddressId;
        OrderStatus status;
        Pricing pricing;
        Timestamps timestamps;
        List<AddOrderItemCommand.Output> items;
        PaymentStatus paymentStatus;

        private static AddOrderOutput of(OrderDomain orderDomain) {
            return new AddOrderOutput(
                    orderDomain.getId(),
                    orderDomain.getCustomerId(),
                    orderDomain.getRestaurantId(),
                    orderDomain.getDeliveryAddressId(),
                    orderDomain.getStatus(),
                    orderDomain.getPricing(),
                    orderDomain.getTimestamps(),
                    orderDomain.getItems().stream()
                            .map(AddOrderItemCommand.Output::of)
                            .toList(),
                    orderDomain.getPaymentStatus()
            );
        }
    }

}
