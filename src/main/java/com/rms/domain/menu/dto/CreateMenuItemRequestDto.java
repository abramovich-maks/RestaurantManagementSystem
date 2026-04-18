package com.rms.domain.menu.dto;

import java.math.BigDecimal;

public record CreateMenuItemRequestDto(
        String name,
        String description,
        BigDecimal price
) {
}