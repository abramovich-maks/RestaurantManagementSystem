package com.rms.infrastructure.menu.controller;

import com.rms.domain.menu.MenuFacade;
import com.rms.infrastructure.menu.controller.dto.AllMenuItemResponse;
import com.rms.infrastructure.menu.controller.dto.CreateMenuItemRequest;
import com.rms.infrastructure.menu.controller.dto.CreateMenuItemResponse;
import com.rms.infrastructure.menu.controller.dto.MenuItemResponse;
import com.rms.infrastructure.menu.controller.dto.UpdateMenuItemRequest;
import com.rms.infrastructure.menu.controller.dto.UpdateMenuItemResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
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
        log.info("Created menu item \"{}\" with id: {}", request.name(), response.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<UpdateMenuItemResponse> editMenuItem(@PathVariable Long itemId, @RequestBody @Valid UpdateMenuItemRequest request) {
        var dto = mapper.toEntity(request);
        var result = menuFacade.editMenuItem(itemId, dto);
        var response = mapper.toUpdateMenuItemResponse(result);
        log.info("Updated menu item with id: {}", itemId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @DeleteMapping("/{itemId}")
    public ResponseEntity<String> deleteMenuItem(@PathVariable Long itemId) {
        menuFacade.deleteMenuItem(itemId);
        log.info("Deleted menu item with id: {}", itemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<AllMenuItemResponse> getAllMenuItems() {
        var items = menuFacade.getAllMenuItems();
        log.info("Retrieved {} menu items", items.size());
        var response = mapper.toAllMenuItemResponse(items);
        return ResponseEntity.ok(new AllMenuItemResponse(response));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<MenuItemResponse> getMenuItem(@PathVariable Long itemId) {
        var item = menuFacade.getMenuItem(itemId);
        var response = mapper.toMenuItemResponse(item);
        log.info("Retrieved menu item with id: {}", itemId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
