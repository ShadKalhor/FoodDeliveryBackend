package com.example.FoodDeliveryBackend.infrastructure.persistence.mapper;

import com.example.FoodDeliveryBackend.domain.model.OrderItemDomain;
import com.example.FoodDeliveryBackend.infrastructure.persistence.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface OrderItemMapper {

    OrderItemDomain toDomain(OrderItem entity);

    OrderItem toEntity(OrderItemDomain domain);
}
