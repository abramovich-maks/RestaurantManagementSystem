package com.rms.domain.menu;

public class MenuItemNotFoundException extends RuntimeException {

    public MenuItemNotFoundException(Long id) {
        super("Menu item not found with id: " + id);
    }
}
