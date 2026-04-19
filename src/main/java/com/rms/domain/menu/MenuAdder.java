package com.rms.domain.menu;

import com.rms.domain.menu.dto.CreateMenuItemRequestDto;
import com.rms.domain.menu.dto.CreateMenuItemResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
class MenuAdder {

    private final MenuItemRepository menuItemRepository;
    private final MenuItemMapper menuItemMapper;
    private final MenuRetriever menuRetriever;

    CreateMenuItemResponseDto addMenuItem(CreateMenuItemRequestDto request) {
        menuRetriever.validateNameNotExists(request.name());
        MenuItemEntity newMenuItem = MenuItemEntity.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price()).build();
        menuItemRepository.save(newMenuItem);
        return menuItemMapper.toCreateMenuItemResponse(newMenuItem);
    }
}


