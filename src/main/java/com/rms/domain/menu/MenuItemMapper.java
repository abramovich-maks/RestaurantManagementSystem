package com.rms.domain.menu;

import com.rms.domain.menu.dto.CreateMenuItemResponseDto;
import com.rms.domain.menu.dto.MenuItemDto;
import com.rms.domain.menu.dto.UpdateMenuItemRequestDto;
import com.rms.domain.menu.dto.UpdateMenuItemResponseDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MenuItemMapper {

    CreateMenuItemResponseDto toCreateMenuItemResponse(MenuItemEntity menuItemEntity);

    MenuItemDto toMenuItemDto(MenuItemEntity menuItemEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(UpdateMenuItemRequestDto request, @MappingTarget MenuItemEntity entity);

    UpdateMenuItemResponseDto toUpdateResponse(MenuItemEntity entity);
}