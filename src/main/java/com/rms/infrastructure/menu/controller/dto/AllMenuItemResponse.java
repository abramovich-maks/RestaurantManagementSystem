package com.rms.infrastructure.menu.controller.dto;

import java.util.List;

public record AllMenuItemResponse(
        List<MenuItemResponse> menuItems
) {
}
