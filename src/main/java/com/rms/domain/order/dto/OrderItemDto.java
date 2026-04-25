package com.rms.domain.order.dto;

import java.math.BigDecimal;

public record OrderItemDto(
        Long id,
        String name,
        int quantity,
        String note,
        BigDecimal price
) {
}
