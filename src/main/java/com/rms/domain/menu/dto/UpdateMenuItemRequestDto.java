package com.rms.domain.menu.dto;

import java.math.BigDecimal;

public record UpdateMenuItemRequestDto(
        String name,
        String description,
        BigDecimal price,
        Boolean isAvailable
) {
}
