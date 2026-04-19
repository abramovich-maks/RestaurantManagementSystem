package com.rms.domain.menu;

import com.rms.domain.menu.dto.MenuItemDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
class MenuRetriever {

    private final MenuItemRepository menuItemRepository;
    private final MenuItemMapper menuItemMapper;

    MenuItemDto getById(final Long id) {
        MenuItemEntity menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException(id));
        return menuItemMapper.toMenuItemDto(menuItem);
    }

    List<MenuItemDto> getAll() {
        return menuItemRepository.findAll().stream()
                .map(menuItemMapper::toMenuItemDto)
                .toList();
    }

    MenuItemEntity getByIdEntity(final Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException(id));
    }

    void validateNameNotExists(String name) {
        if (menuItemRepository.existsByName(name)) {
            throw new MenuItemAlreadyExistsException(name);
        }
    }
}
