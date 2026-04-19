package com.rms.infrastructure.menu.controller;

import com.rms.domain.menu.dto.CreateMenuItemRequestDto;
import com.rms.domain.menu.dto.CreateMenuItemResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MenuItemMapperController {
    CreateMenuItemRequestDto toEntity(CreateMenuItemRequest createMenuItemRequest);

    CreateMenuItemResponse toCreateMenuItemResponse(CreateMenuItemResponseDto responseDto);
}