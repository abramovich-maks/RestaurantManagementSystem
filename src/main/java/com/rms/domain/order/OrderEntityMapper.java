package com.rms.domain.order;

import com.rms.domain.order.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
interface OrderEntityMapper {
    OrderEntity toEntity(OrderDto orderEntityDto);

    OrderDto toOrderEntityDto(OrderEntity orderEntity);
}