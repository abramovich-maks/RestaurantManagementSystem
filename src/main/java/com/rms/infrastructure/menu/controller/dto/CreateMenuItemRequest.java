package com.rms.infrastructure.menu.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateMenuItemRequest(
        @NotBlank
        String name,

        @Size(min = 10, max = 1000)
        @NotBlank
        String description,

        @Positive
        BigDecimal price) {
}