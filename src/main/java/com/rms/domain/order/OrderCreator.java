package com.rms.domain.order;

import com.rms.domain.menu.MenuFacade;
import com.rms.domain.menu.dto.MenuItemDto;
import com.rms.domain.order.dto.OrderCreateRequestDto;
import com.rms.domain.order.dto.OrderCreateResponseDto;
import com.rms.domain.order.dto.OrderItemDto;
import com.rms.domain.order.dto.OrderItemRequestDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
class OrderCreator {

    private final MenuFacade menuFacade;
    private final OrderRepository orderRepository;

    @Transactional
    OrderCreateResponseDto createOrder(final OrderCreateRequestDto requestDto) {
        OrderEntity order = new OrderEntity();
        order.setWaiterId(1L); // todo implement management of employees
        order.setCreatedAt(LocalDateTime.now());
        order.setTableNumber(requestDto.tableNumber());

        if (requestDto.items().isEmpty()) {
            throw new InvalidOrderException("Order must contain at least one item");
        }

        for (OrderItemRequestDto itemRequest : requestDto.items()) {
            MenuItemDto menuItem = menuFacade.getMenuItem(itemRequest.itemId());
            order.addItem(createItem(itemRequest, menuItem));
        }

        BigDecimal total = order.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalPrice(total);
        OrderEntity saved = orderRepository.save(order);


        List<OrderItemDto> itemDtoList = saved.getItems().stream()
                .map(item -> new OrderItemDto(
                        item.getId(),
                        item.getName(),
                        item.getQuantity(),
                        item.getNote(),
                        item.getPrice()
                ))
                .toList();

        return new OrderCreateResponseDto(saved.getId(), saved.getWaiterId(), saved.getCreatedAt(), saved.getTableNumber(), itemDtoList, saved.getTotalPrice());
    }

    private OrderItemEntity createItem(OrderItemRequestDto request, MenuItemDto menuItem) {
        if (request.quantity() <= 0) {
            throw new ValidationItemQuantityException("Quantity must be greater than 0");
        }

        OrderItemEntity item = new OrderItemEntity();
        item.setName(menuItem.name());
        item.setPrice(menuItem.price());
        item.setQuantity(request.quantity());
        item.setNote(request.note());

        return item;
    }
}