package com.rms.domain.menu.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CreateMenuItemResponseDto(
        Long id,
        String name,
        String description,
        BigDecimal price
) {
}
