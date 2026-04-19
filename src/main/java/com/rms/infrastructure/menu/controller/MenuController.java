package com.rms.infrastructure.menu.controller;

import com.rms.domain.menu.MenuFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
class MenuController {

    private final MenuFacade menuFacade;
    private final MenuItemMapperController mapper;

    @PostMapping
    public ResponseEntity<CreateMenuItemResponse> createMenuItem(@RequestBody @Valid CreateMenuItemRequest request) {
        var dto = mapper.toEntity(request);
        var result = menuFacade.addMenuItem(dto);
        var response = mapper.toCreateMenuItemResponse(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
