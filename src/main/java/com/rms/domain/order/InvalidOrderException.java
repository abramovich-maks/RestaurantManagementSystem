package com.rms.domain.order;

import lombok.Getter;

@Getter
public class InvalidOrderException extends RuntimeException {

    public InvalidOrderException(String message) {
        super(message);

    }
}

