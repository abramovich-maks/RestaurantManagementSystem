package com.rms.domain.order.dto;

public record OrderItemRequestDto(
        Long itemId,
        int quantity,
        String note
) {
}
