package com.rms.infrastructure.api.validation;

import com.rms.domain.order.ValidationItemQuantityException;
import com.rms.infrastructure.api.ApiErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Log4j2
class ValidationErrorHandler {

    @ExceptionHandler(ValidationItemQuantityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleValidationItemQuantityException(ValidationItemQuantityException exception) {
        log.warn(exception.getMessage());
        return new ApiErrorResponse("ITEM_QUANTITY_MUST_BE_POSITIVE", exception.getMessage());
    }
}