package com.rms.infrastructure.api.notfound;

import com.rms.domain.menu.MenuItemNotFoundException;
import com.rms.domain.order.OrderNotFoundException;
import com.rms.infrastructure.api.ApiErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
class NotFoundErrorHandler {

    @ExceptionHandler(MenuItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleMenuItemNotFound(MenuItemNotFoundException exception) {
        log.warn("Menu item not found with id: {}", exception.getId());
        return new ApiErrorResponse("MENU_ITEM_NOT_FOUND", exception.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleOrderNotFound(OrderNotFoundException exception) {
        log.warn("Order not found with id: {}", exception.getId());
        return new ApiErrorResponse("ORDER_NOT_FOUND", exception.getMessage());
    }
}