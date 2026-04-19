package com.rms.domain.menu;

public class MenuItemAlreadyExistsException extends RuntimeException {
    public MenuItemAlreadyExistsException(String name) {
        super("Menu item with name: \"" + name + "\" already exists.");
    }
}
