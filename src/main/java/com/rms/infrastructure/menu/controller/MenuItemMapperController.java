package com.rms.infrastructure.menu.controller;

import com.rms.domain.menu.dto.CreateMenuItemRequestDto;
import com.rms.domain.menu.dto.CreateMenuItemResponseDto;
import com.rms.domain.menu.dto.MenuItemDto;
import com.rms.domain.menu.dto.UpdateMenuItemRequestDto;
import com.rms.domain.menu.dto.UpdateMenuItemResponseDto;
import com.rms.infrastructure.menu.controller.dto.CreateMenuItemRequest;
import com.rms.infrastructure.menu.controller.dto.CreateMenuItemResponse;
import com.rms.infrastructure.menu.controller.dto.MenuItemResponse;
import com.rms.infrastructure.menu.controller.dto.UpdateMenuItemRequest;
import com.rms.infrastructure.menu.controller.dto.UpdateMenuItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MenuItemMapperController {
    CreateMenuItemRequestDto toEntity(CreateMenuItemRequest createMenuItemRequest);

    CreateMenuItemResponse toCreateMenuItemResponse(CreateMenuItemResponseDto responseDto);

    UpdateMenuItemRequestDto toEntity(UpdateMenuItemRequest updateMenuItemRequest);

    UpdateMenuItemResponse toUpdateMenuItemResponse(UpdateMenuItemResponseDto responseDto);

    MenuItemResponse toMenuItemResponse(MenuItemDto item);

    List<MenuItemResponse> toAllMenuItemResponse(List<MenuItemDto> items);
}