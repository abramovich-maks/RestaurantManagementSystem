package com.rms.domain.menu;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
class MenuDeleter {

    private final MenuItemRepository menuItemRepository;

    @Transactional
    void deleteMenuItem(final Long id) {
        if (!menuItemRepository.existsById(id)) {
            throw new MenuItemNotFoundException(id);
        }
        menuItemRepository.deleteById(id);
    }
}
