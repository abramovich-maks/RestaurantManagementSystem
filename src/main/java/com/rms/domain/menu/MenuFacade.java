package com.rms.domain.menu;

import com.rms.domain.menu.dto.CreateMenuItemRequestDto;
import com.rms.domain.menu.dto.CreateMenuItemResponseDto;
import com.rms.domain.menu.dto.MenuItemDto;
import com.rms.domain.menu.dto.UpdateMenuItemRequestDto;
import com.rms.domain.menu.dto.UpdateMenuItemResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuFacade {

    private final MenuAdder menuAdder;
    private final MenuEditor menuEditor;
    private final MenuDeleter menuDeleter;
    private final MenuRetriever menuRetriever;

    public CreateMenuItemResponseDto addMenuItem(CreateMenuItemRequestDto request) {
        return menuAdder.addMenuItem(request);
    }

    public UpdateMenuItemResponseDto editMenuItem(Long id, UpdateMenuItemRequestDto request) {
        return menuEditor.editMenuItem(id, request);
    }

    public void deleteMenuItem(Long id) {
        menuDeleter.deleteMenuItem(id);
    }

    public MenuItemDto getMenuItem(Long id) {
        return menuRetriever.getById(id);
    }

    public List<MenuItemDto> getAllMenuItems() {
        return menuRetriever.getAll();
    }
}
