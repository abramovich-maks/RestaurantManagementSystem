package com.rms.infrastructure.menu.controller.dto;

import java.math.BigDecimal;

public record UpdateMenuItemResponse(
        String name,
        String description,
        BigDecimal price,
        Boolean available
) {
}
