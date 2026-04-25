package com.rms.domain.order.dto;

import java.math.BigDecimal;

public record OrderItemRequestDto(
        Long itemId,
        int quantity,
        String note,
        BigDecimal price
) {
}
