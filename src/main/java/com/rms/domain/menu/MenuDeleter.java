package com.rms.domain.menu;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
class MenuDeleter {

    private final MenuItemRepository menuItemRepository;

    void deleteMenuItem(final Long id) {
        if (!menuItemRepository.existsById(id)) {
            throw new MenuItemNotFoundException(id);
        }
        menuItemRepository.deleteById(id);
    }
}
