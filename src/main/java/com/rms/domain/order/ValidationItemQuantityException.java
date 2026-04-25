package com.rms.domain.order;

import lombok.Getter;

@Getter
public class ValidationItemQuantityException extends RuntimeException {

    public ValidationItemQuantityException(String message) {
        super(message);
    }
}
