package com.rms.infrastructure.api;

public record ApiErrorResponse(
        String code,
        String message
) {
}
