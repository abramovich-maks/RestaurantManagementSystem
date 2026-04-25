package com.rms.domain.order.dto;

import java.util.List;

public record OrderCreateRequestDto(
        Integer tableNumber,
        List<OrderItemRequestDto> items
) {
}
