package com.rms.domain.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderCreateResponseDto(
        Long id,
        Long waiterId,
        LocalDateTime createdAt,
        Integer tableNumber,
        List<OrderItemDto> items,
        BigDecimal totalPrice

) {
}
