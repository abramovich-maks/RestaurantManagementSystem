package com.rms.domain.menu;

import lombok.Getter;

@Getter
public class MenuItemNotFoundException extends RuntimeException {

    private final Long id;

    public MenuItemNotFoundException(Long id) {
        super("Menu item not found with id: " + id);
        this.id = id;
    }
}
