package com.rms.domain.menu.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record UpdateMenuItemResponseDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Boolean isAvailable
) {
}