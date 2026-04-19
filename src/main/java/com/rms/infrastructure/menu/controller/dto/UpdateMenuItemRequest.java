package com.rms.infrastructure.menu.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateMenuItemRequest(
        @Size(min = 1)
        String name,

        @Size(min = 1)
        String description,

        @Positive
        @NotNull
        BigDecimal price,
        Boolean available
) {
}