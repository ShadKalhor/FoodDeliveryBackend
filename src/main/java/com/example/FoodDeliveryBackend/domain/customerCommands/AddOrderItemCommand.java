package com.example.FoodDeliveryBackend.domain.customerCommands;

import com.example.FoodDeliveryBackend.domain.exception.ErrorType;
import com.example.FoodDeliveryBackend.domain.exception.StructuredError;
import com.example.FoodDeliveryBackend.domain.model.OrderItemDomain;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

public class AddOrderItemCommand {


    public Either<StructuredError, OrderItemDomain> execute(Input input){

        return Try.of(() -> {
            OrderItemDomain orderItemDomain = input.toParams();
            orderItemDomain.setLineTotal(orderItemDomain.getUnitPriceSnapshot()
                    .multiply(BigDecimal.valueOf(orderItemDomain.getQuantity())));
            return orderItemDomain;
        }).toEither(new StructuredError("Unexpected Error While Creating Item", ErrorType.SERVER_ERROR));

    }


    @Value
    public static class Input{

        UUID orderId;
        UUID menuItemId;
        String nameSnapshot;
        BigDecimal unitPriceSnapshot;
        int quantity;


        private OrderItemDomain toParams(){
            return new OrderItemDomain(UUID.randomUUID(),orderId,menuItemId,nameSnapshot,unitPriceSnapshot,quantity,null);
        }

    }

    @Value
    public static class Output{

        UUID orderId;

        static Output of(OrderItemDomain orderItemDomain){
            return new Output(orderItemDomain.getId());
        }

    }


}
