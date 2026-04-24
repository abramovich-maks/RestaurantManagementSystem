package com.rms.domain.menu;

import lombok.Getter;

@Getter
public class MenuItemAlreadyExistsException extends RuntimeException {

    private final String name;

    public MenuItemAlreadyExistsException(String name) {
        super("Menu item with name: \"" + name + "\" already exists.");
        this.name = name;
    }
}
