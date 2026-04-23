package com.example.FoodDeliveryBackend.infrastructure.persistence.mapper;

import com.example.FoodDeliveryBackend.domain.model.OrderDomain;
import com.example.FoodDeliveryBackend.infrastructure.persistence.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface OrderMapper {

    @Mapping(target = "items", ignore = true)
    OrderDomain toDomain(Order entity);

    Order toEntity(OrderDomain orderDomain);




}
