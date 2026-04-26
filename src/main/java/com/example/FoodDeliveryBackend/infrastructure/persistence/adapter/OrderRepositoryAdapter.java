package com.example.FoodDeliveryBackend.infrastructure.persistence.adapter;

import com.example.FoodDeliveryBackend.domain.exception.ErrorType;
import com.example.FoodDeliveryBackend.domain.exception.StructuredError;
import com.example.FoodDeliveryBackend.domain.model.OrderDomain;
import com.example.FoodDeliveryBackend.domain.port.out.OrderRepositoryPort;
import com.example.FoodDeliveryBackend.infrastructure.persistence.entity.OrderItem;
import com.example.FoodDeliveryBackend.infrastructure.persistence.mapper.OrderItemMapper;
import com.example.FoodDeliveryBackend.infrastructure.persistence.mapper.OrderMapper;
import com.example.FoodDeliveryBackend.infrastructure.persistence.repository.OrderItemRepositoryJPA;
import com.example.FoodDeliveryBackend.infrastructure.persistence.repository.OrderRepositoryJPA;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;



@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepositoryPort {

    private final OrderRepositoryJPA orderRepositoryJPA;
    private final OrderItemRepositoryJPA orderItemRepositoryJPA;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Transactional
    @Override
    public Either<StructuredError, OrderDomain> save(OrderDomain domain) {

        return Try.run(() -> {
            List<OrderItem> itemEntities = domain.getItems().stream().map(orderItemMapper::toEntity).toList();
            orderItemRepositoryJPA.saveAll(itemEntities);
        }).toEither(new StructuredError("Error While Saving Order Items", ErrorType.DATABASE_ERROR))
                .flatMap(ignored ->
                        Try.of(() ->orderRepositoryJPA.save(orderMapper.toEntity(domain)))
                                .map(orderMapper::toDomain)
                                .toEither(() -> new StructuredError("Error While Saving Order",
                                        ErrorType.DATABASE_ERROR)));
    }

    @Override
    public Either<StructuredError, OrderDomain> findById(UUID orderId) {
        return Try.of(() -> orderRepositoryJPA.findOptionById(orderId))
                .toEither(() -> new StructuredError("Error While Getting Order",ErrorType.DATABASE_ERROR))
                .flatMap(option -> option.map(orderMapper::toDomain)
                        .toEither(() -> new StructuredError("Error While Mapping The Returned Value of FindOrderById",ErrorType.SERVER_ERROR)));
    }
}
