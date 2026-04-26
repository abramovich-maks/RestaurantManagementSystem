package com.rms.domain.order;

import lombok.Getter;

@Getter
public class OrderNotFoundException extends RuntimeException {

    private final Long id;

    public OrderNotFoundException(Long id) {
        super("Order not found with id: " + id);
        this.id = id;
    }
}
