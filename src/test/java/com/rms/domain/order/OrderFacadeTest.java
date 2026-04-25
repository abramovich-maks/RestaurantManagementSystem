package com.rms.domain.order;

import com.rms.domain.menu.MenuFacade;
import com.rms.domain.menu.MenuItemNotFoundException;
import com.rms.domain.menu.dto.MenuItemDto;
import com.rms.domain.order.dto.OrderCreateRequestDto;
import com.rms.domain.order.dto.OrderCreateResponseDto;
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
    OrderFacade orderFacade = new OrderFacade(orderRetriever, orderCreator);

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
}