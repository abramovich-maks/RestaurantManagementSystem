package com.rms.infrastructure.menu.controller;

import java.math.BigDecimal;

public record CreateMenuItemResponse(
        Long id,
        String name,
        String description,
        BigDecimal price
) {
}