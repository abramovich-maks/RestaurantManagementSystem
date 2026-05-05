package com.rms.domain.order;

import lombok.Getter;

@Getter
public class OrderItemNotFoundException extends RuntimeException {

    private final Long orderId;
    private final Long itemId;

    public OrderItemNotFoundException(Long orderId, Long itemId) {
        super("Order with id: " + orderId + " does not contain item with id: " + itemId);
        this.orderId = orderId;
        this.itemId = itemId;
    }
}
