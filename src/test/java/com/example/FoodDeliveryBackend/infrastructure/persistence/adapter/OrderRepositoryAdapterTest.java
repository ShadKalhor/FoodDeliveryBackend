package com.example.FoodDeliveryBackend.infrastructure.persistence.adapter;

import com.example.FoodDeliveryBackend.domain.enums.OrderStatus;
import com.example.FoodDeliveryBackend.domain.enums.PaymentStatus;
import com.example.FoodDeliveryBackend.domain.exception.StructuredError;
import com.example.FoodDeliveryBackend.domain.model.OrderDomain;
import com.example.FoodDeliveryBackend.domain.model.OrderItemDomain;
import com.example.FoodDeliveryBackend.domain.model.Pricing;
import com.example.FoodDeliveryBackend.domain.model.Timestamps;
import com.example.FoodDeliveryBackend.infrastructure.persistence.entity.MenuItem;
import com.example.FoodDeliveryBackend.infrastructure.persistence.entity.Order;
import com.example.FoodDeliveryBackend.infrastructure.persistence.entity.OrderItem;
import com.example.FoodDeliveryBackend.infrastructure.persistence.mapper.OrderItemMapper;
import com.example.FoodDeliveryBackend.infrastructure.persistence.mapper.OrderMapper;
import com.example.FoodDeliveryBackend.infrastructure.persistence.repository.OrderItemRepositoryJPA;
import com.example.FoodDeliveryBackend.infrastructure.persistence.repository.OrderRepositoryJPA;
import io.vavr.control.Either;
import io.vavr.control.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderRepositoryAdapterTest {

    @Mock
    private OrderRepositoryJPA orderRepositoryJPA;

    @Mock
    private OrderItemRepositoryJPA orderItemRepositoryJPA;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderItemMapper orderItemMapper;


    @Captor
    ArgumentCaptor<List<OrderItem>> itemsCaptor;

    private OrderRepositoryAdapter orderRepositoryAdapter;

    @BeforeEach
    void setUp() {
        orderRepositoryAdapter = new OrderRepositoryAdapter(
                orderRepositoryJPA,
                orderItemRepositoryJPA,
                orderMapper,
                orderItemMapper
        );
    }

    @Test
    void shouldSaveOrderSuccessfully() {
        OrderDomain domain = buildOrderDomain();
        OrderItemDomain itemDomain = domain.getItems().getFirst();

        Order mappedOrderEntity = new Order();
        Order savedOrderEntity = new Order();
        OrderItem mappedItemEntity = new OrderItem();

        when(orderItemMapper.toEntity(itemDomain)).thenReturn(mappedItemEntity);
        when(orderMapper.toEntity(domain)).thenReturn(mappedOrderEntity);
        when(orderRepositoryJPA.save(mappedOrderEntity)).thenReturn(savedOrderEntity);
        when(orderMapper.toDomain(savedOrderEntity)).thenReturn(domain);

        Either<StructuredError, OrderDomain> result = orderRepositoryAdapter.save(domain);

        assertTrue(result.isRight());
        assertSame(domain, result.get());

        verify(orderItemRepositoryJPA).saveAll(itemsCaptor.capture());
        assertEquals(1, itemsCaptor.getValue().size());
        assertSame(mappedItemEntity, itemsCaptor.getValue().getFirst());

        verify(orderRepositoryJPA).save(mappedOrderEntity);
    }

    @Test
    void shouldReturnLeftWhenSavingItemsFails() {
        OrderDomain domain = buildOrderDomain();
        OrderItemDomain itemDomain = domain.getItems().getFirst();

        Order mappedOrderEntity = new Order();
        mappedOrderEntity.setId(itemDomain.getOrderId());

        MenuItem mappedMenuEntity = new MenuItem();
        mappedMenuEntity.setId(itemDomain.getMenuItemId());

        OrderItem mappedItemEntity = new OrderItem(itemDomain.getId(),mappedOrderEntity,mappedMenuEntity, itemDomain.getNameSnapshot(), itemDomain.getUnitPriceSnapshot(), itemDomain.getQuantity(),itemDomain.getLineTotal());

        when(orderItemMapper.toEntity(itemDomain)).thenReturn(mappedItemEntity);
        doThrow(new RuntimeException("db down")).when(orderItemRepositoryJPA).saveAll(anyList());

        Either<StructuredError, OrderDomain> result = orderRepositoryAdapter.save(domain);

        assertTrue(result.isLeft());
        verify(orderRepositoryJPA, never()).save(any());
    }

    @Test
    void shouldFindOrderByIdSuccessfully() {
        UUID orderId = UUID.randomUUID();
        Order entity = new Order();
        OrderDomain domain = buildOrderDomain();

        when(orderRepositoryJPA.findOptionById(orderId)).thenReturn(Option.of(entity));
        when(orderMapper.toDomain(entity)).thenReturn(domain);

        Either<StructuredError, OrderDomain> result = orderRepositoryAdapter.findById(orderId);

        assertTrue(result.isRight());
        assertSame(domain, result.get());
    }

    @Test
    void shouldReturnLeftWhenOrderIsNotFound() {
        UUID orderId = UUID.randomUUID();

        when(orderRepositoryJPA.findOptionById(orderId)).thenReturn(Option.none());

        Either<StructuredError, OrderDomain> result = orderRepositoryAdapter.findById(orderId);

        assertTrue(result.isLeft());
    }

    private OrderDomain buildOrderDomain() {
        UUID orderId = UUID.randomUUID();

        OrderItemDomain item = new OrderItemDomain(
                UUID.randomUUID(),
                orderId,
                UUID.randomUUID(),
                "Burger",
                BigDecimal.valueOf(12_000),
                2,
                BigDecimal.valueOf(24_000)
        );

        return new OrderDomain(
                orderId,
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                OrderStatus.DRAFT,
                new Pricing(
                        BigDecimal.valueOf(24_000),
                        BigDecimal.valueOf(2_000),
                        BigDecimal.valueOf(1_000),
                        BigDecimal.valueOf(0),
                        BigDecimal.valueOf(27_000)
                ),
                new Timestamps(Instant.now()),
                List.of(item),
                PaymentStatus.PENDING
        );
    }
}
