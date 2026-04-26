package com.rms.domain.order;

import com.rms.domain.menu.MenuFacade;
import com.rms.domain.menu.MenuItemNotFoundException;
import com.rms.domain.menu.dto.MenuItemDto;
import com.rms.domain.order.dto.OrderCreateRequestDto;
import com.rms.domain.order.dto.OrderCreateResponseDto;
import com.rms.domain.order.dto.OrderDto;
import com.rms.domain.order.dto.OrderItemDto;
import com.rms.domain.order.dto.OrderItemRequestDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderFacadeTest {

    MenuFacade menuFacade = mock(MenuFacade.class);

    OrderRepository orderRepository = new OrderRepositoryTestImpl();
    OrderEntityMapper orderEntityMapper = new OrderEntityMapperImpl();
    OrderRetriever orderRetriever = new OrderRetriever(orderRepository, orderEntityMapper);
    OrderCreator orderCreator = new OrderCreator(menuFacade, orderRepository);
    OrderUpdater orderUpdater = new OrderUpdater(orderRetriever, menuFacade, orderEntityMapper);
    OrderFacade orderFacade = new OrderFacade(orderRetriever, orderCreator, orderUpdater);

    @Test
    void should_create_order() {
        // given
        when(menuFacade.getMenuItem(1L)).thenReturn(new MenuItemDto(1L, "Pizza", "desc", BigDecimal.valueOf(15), true));
        OrderCreateRequestDto request = new OrderCreateRequestDto(1,
                List.of(new OrderItemRequestDto(1L, 2, "no onion")));
        // when
        OrderCreateResponseDto response = orderFacade.createOrder(request);
        // then
        assertThat(response.id()).isNotNull();
        assertThat(response.tableNumber()).isEqualTo(1);
        assertThat(response.items()).hasSize(1);

        OrderItemDto item = response.items().get(0);
        assertThat(item.name()).isEqualTo("Pizza");
        assertThat(item.quantity()).isEqualTo(2);
        assertThat(item.note()).isEqualTo("no onion");

        assertThat(response.totalPrice()).isEqualTo(BigDecimal.valueOf(30));
    }

    @Test
    void should_create_order_with_multiple_items() {
        when(menuFacade.getMenuItem(1L)).thenReturn(new MenuItemDto(1L, "Pizza", "desc", BigDecimal.valueOf(10), true));
        when(menuFacade.getMenuItem(2L)).thenReturn(new MenuItemDto(2L, "Coffee", "desc", BigDecimal.valueOf(5), true));

        OrderCreateRequestDto request = new OrderCreateRequestDto(1,
                List.of(new OrderItemRequestDto(1L, 2, null),
                        new OrderItemRequestDto(2L, 3, null)));
        OrderCreateResponseDto response = orderFacade.createOrder(request);
        assertThat(response.totalPrice()).isEqualTo(BigDecimal.valueOf(35));
    }

    @Test
    void should_exception_when_quantity_is_less_than_1() {
        // given
        when(menuFacade.getMenuItem(1L)).thenReturn(new MenuItemDto(1L, "Pizza", "desc", BigDecimal.valueOf(15), true));
        OrderCreateRequestDto request = new OrderCreateRequestDto(1,
                List.of(new OrderItemRequestDto(1L, 0, "no onion")));
        // when && then
        ValidationItemQuantityException exception = assertThrows(ValidationItemQuantityException.class, () -> orderFacade.createOrder(request));
        assertThat(exception.getMessage()).isEqualTo("Quantity must be greater than 0");
    }

    @Test
    void should_exception_when_order_not_have_item() {
        // given
        when(menuFacade.getMenuItem(1L)).thenReturn(new MenuItemDto(1L, "Pizza", "desc", BigDecimal.valueOf(15), true));
        OrderCreateRequestDto request = new OrderCreateRequestDto(1, List.of());
        // when && then
        InvalidOrderException exception = assertThrows(InvalidOrderException.class, () -> orderFacade.createOrder(request));
        assertThat(exception.getMessage()).isEqualTo("Order must contain at least one item");
    }

    @Test
    void should_throw_when_menu_item_not_found() {
        when(menuFacade.getMenuItem(1L)).thenThrow(new MenuItemNotFoundException(1L));

        OrderCreateRequestDto request = new OrderCreateRequestDto(1,
                List.of(new OrderItemRequestDto(1L, 2, null))
        );

        assertThatThrownBy(() -> orderFacade.createOrder(request))
                .isInstanceOf(MenuItemNotFoundException.class);
    }

    @Test
    void should_get_order_by_id() {
        // given
        when(menuFacade.getMenuItem(1L)).thenReturn(new MenuItemDto(1L, "Pizza", "desc", BigDecimal.valueOf(15), true));
        OrderCreateRequestDto request = new OrderCreateRequestDto(1,
                List.of(new OrderItemRequestDto(1L, 2, "no onion")));
        OrderCreateResponseDto response = orderFacade.createOrder(request);
        // when
        OrderDto order = orderFacade.getById(response.id());
        // then
        assertThat(order.id()).isEqualTo(response.id());
        assertThat(order.tableNumber()).isEqualTo(response.tableNumber());
        assertThat(order.items()).hasSize(1);

        OrderItemDto item = order.items().get(0);
        assertThat(item.name()).isEqualTo("Pizza");
        assertThat(item.quantity()).isEqualTo(2);
    }

    @Test
    void should_exception_when_order_not_found() {
        // given
        long id = 1L;
        // when && then
        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> orderFacade.getById(id));
        assertThat(exception.getMessage()).isEqualTo("Order not found with id: " + id);
    }

    @Test
    void should_exception_when_add_item_to_existing_order_and_quantity_not_positive() {
        // given
        when(menuFacade.getMenuItem(1L)).thenReturn(new MenuItemDto(1L, "Pizza", "desc", BigDecimal.valueOf(15), true));
        OrderCreateRequestDto request = new OrderCreateRequestDto(1,
                List.of(new OrderItemRequestDto(1L, 2, "no onion")));
        OrderCreateResponseDto response = orderFacade.createOrder(request);
        // when && then
        ValidationItemQuantityException exception = assertThrows(ValidationItemQuantityException.class, () -> orderFacade.addItemToOrder(response.id(), new OrderItemRequestDto(1L, 0, null)));
        assertThat(exception.getMessage()).isEqualTo("Quantity must be greater than 0");
    }

    @Test
    void should_add_item_to_existing_order() {
        // given
        when(menuFacade.getMenuItem(1L)).thenReturn(new MenuItemDto(1L, "Pizza", "desc", BigDecimal.valueOf(15), true));
        when(menuFacade.getMenuItem(2L)).thenReturn(new MenuItemDto(2L, "Coffee", "desc", BigDecimal.valueOf(5), true));

        OrderCreateRequestDto request = new OrderCreateRequestDto(1,
                List.of(new OrderItemRequestDto(1L, 2, "no onion")));
        OrderCreateResponseDto response = orderFacade.createOrder(request);

        OrderDto order = orderFacade.getById(response.id());

        assertThat(order.items()).hasSize(1);
        assertThat(order.totalPrice()).isEqualTo(BigDecimal.valueOf(30));
        // when
        OrderItemRequestDto newRequest = new OrderItemRequestDto(2L, 1, null);
        OrderDto updatedOrder = orderFacade.addItemToOrder(response.id(), newRequest);
        // then
        assertThat(updatedOrder.items()).hasSize(2);
        assertThat(updatedOrder.totalPrice()).isEqualTo(BigDecimal.valueOf(35));
    }

    @Test
    void should_save_note_when_item_exists_and_note_exists() {
        // given
        when(menuFacade.getMenuItem(1L)).thenReturn(new MenuItemDto(1L, "Pizza", "desc", BigDecimal.valueOf(15), true));

        String firstNote = "first note";
        String secondNote = "second note";

        OrderCreateRequestDto request = new OrderCreateRequestDto(1,
                List.of(new OrderItemRequestDto(1L, 2, firstNote)));
        OrderCreateResponseDto response = orderFacade.createOrder(request);
        // when
        OrderItemRequestDto newRequest = new OrderItemRequestDto(1L, 1, secondNote);
        orderFacade.addItemToOrder(response.id(), newRequest);
        // then
        OrderDto order = orderFacade.getById(response.id());

        assertThat(order.items()).hasSize(1);
        assertThat(order.items().get(0).note()).isEqualTo(firstNote + "\n" + secondNote);
        assertThat(order.totalPrice()).isEqualTo(BigDecimal.valueOf(45));
    }

    @Test
    void should_set_note_when_existing_item_has_no_note() {
        // given
        when(menuFacade.getMenuItem(1L)).thenReturn(new MenuItemDto(1L, "Pizza", "desc", BigDecimal.valueOf(15), true));

        OrderCreateRequestDto request = new OrderCreateRequestDto(1,
                List.of(new OrderItemRequestDto(1L, 2, null)));
        OrderCreateResponseDto response = orderFacade.createOrder(request);
        // when
        OrderItemRequestDto newRequest = new OrderItemRequestDto(1L, 1, "note");
        orderFacade.addItemToOrder(response.id(), newRequest);
        // then
        OrderDto order = orderFacade.getById(response.id());

        assertThat(order.items()).hasSize(1);
        assertThat(order.items().get(0).note()).isEqualTo("note");
        assertThat(order.totalPrice()).isEqualTo(BigDecimal.valueOf(45));
    }

    @Test
    void should_increase_quantity_when_item_already_exists() {
        // given
        when(menuFacade.getMenuItem(1L)).thenReturn(new MenuItemDto(1L, "Pizza", "desc", BigDecimal.valueOf(10), true));
        OrderCreateResponseDto response = orderFacade.createOrder(new OrderCreateRequestDto(1, List.of(new OrderItemRequestDto(1L, 2, null))));
        // when
        orderFacade.addItemToOrder(response.id(), new OrderItemRequestDto(1L, 3, null));
        // then
        OrderDto order = orderFacade.getById(response.id());

        assertThat(order.items()).hasSize(1);
        assertThat(order.items().get(0).quantity()).isEqualTo(5);
    }
}