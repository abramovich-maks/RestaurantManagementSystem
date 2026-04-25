package com.rms.infrastructure.api.business;

import com.rms.domain.menu.MenuItemAlreadyExistsException;
import com.rms.domain.order.InvalidOrderException;
import com.rms.infrastructure.api.ApiErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Log4j2
class BusinessErrorHandler {

    @ExceptionHandler(MenuItemAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleMenuItemAlreadyExists(MenuItemAlreadyExistsException exception) {
        log.warn("Menu item already exists. name={}", exception.getName());
        return new ApiErrorResponse("MENU_ITEM_ALREADY_EXISTS", exception.getMessage());
    }

    @ExceptionHandler(InvalidOrderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleInvalidOrderException(InvalidOrderException exception) {
        log.warn(exception.getMessage());
        return new ApiErrorResponse("ORDER_MUST_HAVE_ITEMS", exception.getMessage());
    }
}