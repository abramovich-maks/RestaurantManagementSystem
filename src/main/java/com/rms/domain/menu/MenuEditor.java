package com.rms.domain.menu;

import com.rms.domain.menu.dto.UpdateMenuItemRequestDto;
import com.rms.domain.menu.dto.UpdateMenuItemResponseDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
class MenuEditor {

    private final MenuRetriever menuRetriever;
    private final MenuItemMapper menuItemMapper;

    @Transactional
    UpdateMenuItemResponseDto editMenuItem(final Long id, final UpdateMenuItemRequestDto request) {
        MenuItemEntity entity = menuRetriever.getByIdEntity(id);
        menuItemMapper.updateEntityFromRequest(request, entity);
        return menuItemMapper.toUpdateResponse(entity);
    }
}

