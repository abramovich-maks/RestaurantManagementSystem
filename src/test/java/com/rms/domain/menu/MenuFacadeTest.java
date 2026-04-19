package com.rms.domain.menu;

import com.rms.domain.menu.dto.CreateMenuItemRequestDto;
import com.rms.domain.menu.dto.UpdateMenuItemRequestDto;
import com.rms.domain.menu.dto.UpdateMenuItemResponseDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MenuFacadeTest {

    MenuItemRepository repository = new MenuItemRepositoryTestImpl();
    MenuItemMapper menuItemMapper = new MenuItemMapperImpl();
    MenuRetriever menuRetriever = new MenuRetriever(repository, menuItemMapper);
    MenuAdder menuAdder = new MenuAdder(repository, menuItemMapper, menuRetriever);
    MenuEditor menuEditor = new MenuEditor(menuRetriever, menuItemMapper);
    MenuDeleter menuDeleter = new MenuDeleter(repository);
    MenuFacade facade = new MenuFacade(menuAdder, menuEditor, menuDeleter, menuRetriever);

    @Test
    public void should_create_menu_item() {
        // given
        CreateMenuItemRequestDto requestDto = new CreateMenuItemRequestDto("Pizza", "Pizza with tomato", BigDecimal.valueOf(100));
        // when
        var request = facade.addMenuItem(requestDto);
        // then
        assertThat(request.id()).isNotNull();
        assertThat(request.name()).isEqualTo("Pizza");
        assertThat(request.description()).isEqualTo("Pizza with tomato");
        assertThat(request.price()).isEqualTo(BigDecimal.valueOf(100));
    }

    @Test
    public void should_exceptions_when_menu_item_already_exists() {
        // given
        CreateMenuItemRequestDto requestDto = new CreateMenuItemRequestDto("Pizza", "Pizza with tomato", BigDecimal.valueOf(100));
        facade.addMenuItem(requestDto);
        // when & then
        MenuItemAlreadyExistsException exception = assertThrows(MenuItemAlreadyExistsException.class, () -> facade.addMenuItem(requestDto));
        assertThat(facade.getAllMenuItems()).hasSize(1);
        assertThat(exception.getMessage()).isEqualTo("Menu item with name: \"Pizza\" already exists.");
    }

    @Test
    public void should_edit_menu_item() {
        // given
        CreateMenuItemRequestDto requestDto = new CreateMenuItemRequestDto("Pizza", "Pizza with tomato", BigDecimal.valueOf(100));
        var created = facade.addMenuItem(requestDto);
        // when
        UpdateMenuItemResponseDto updateMenuItem = facade.editMenuItem(created.id(), UpdateMenuItemRequestDto.builder().description("Pizza with cheese").build());
        assertThat(updateMenuItem.name()).isEqualTo("Pizza");
        assertThat(updateMenuItem.description()).isEqualTo("Pizza with cheese");
    }

    @Test
    public void should_exceptions_when_menu_item_not_found() {
        // given
        long id = 0L;
        UpdateMenuItemRequestDto requestDto = UpdateMenuItemRequestDto.builder().description("Pizza with cheese").build();
        // when & then
        MenuItemNotFoundException exception = assertThrows(MenuItemNotFoundException.class, () -> facade.editMenuItem(id, requestDto));
        assertThat(exception.getMessage()).isEqualTo("Menu item not found with id: " + id);
    }

    @Test
    public void should_delete_menu_item() {
        // given
        CreateMenuItemRequestDto requestDto = new CreateMenuItemRequestDto("Pizza", "Pizza with tomato", BigDecimal.valueOf(100));
        var created = facade.addMenuItem(requestDto);
        var oneItem = facade.getAllMenuItems();
        assertThat(oneItem.size()).isEqualTo(1);
        // when
        facade.deleteMenuItem(created.id());
        // then
        var allItems = facade.getAllMenuItems();
        assertThat(allItems.size()).isEqualTo(0);
        assertThrows(MenuItemNotFoundException.class, () -> facade.getMenuItem(created.id()));
    }

    @Test
    public void should_exceptions_when_menu_item_not_found_when_deleting() {
        // given
        long id = 0L;
        // when & then
        MenuItemNotFoundException exception = assertThrows(MenuItemNotFoundException.class, () -> facade.deleteMenuItem(id));
        assertThat(exception.getMessage()).isEqualTo("Menu item not found with id: " + id);
    }

    @Test
    public void should_get_menu_item_by_id() {
        // given
        CreateMenuItemRequestDto requestDto = new CreateMenuItemRequestDto("Pizza", "Pizza with tomato", BigDecimal.valueOf(100));
        var created = facade.addMenuItem(requestDto);
        // when
        var item = facade.getMenuItem(created.id());
        // then
        assertThat(item.id()).isEqualTo(1L);
        assertThat(item.name()).isEqualTo("Pizza");
        assertThat(item.description()).isEqualTo("Pizza with tomato");
        assertThat(item.price()).isEqualTo(BigDecimal.valueOf(100));
    }
}