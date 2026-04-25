package com.rms.domain.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        Long id,
        Long waiterId,
        LocalDateTime createdAt,
        List<OrderItemDto> items,
        Integer tableNumber,
        BigDecimal totalPrice
) {
}
