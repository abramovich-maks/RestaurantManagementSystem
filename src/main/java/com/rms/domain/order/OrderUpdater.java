package com.rms.domain.order;

import com.rms.domain.menu.MenuFacade;
import com.rms.domain.menu.dto.MenuItemDto;
import com.rms.domain.order.dto.OrderDto;
import com.rms.domain.order.dto.OrderItemRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
class OrderUpdater {

    private final OrderRetriever orderRetriever;
    private final MenuFacade menuFacade;
    private final OrderEntityMapper orderEntityMapper;

    @Transactional
    OrderDto addItem(final Long orderId, final OrderItemRequestDto request) {
        if (request.quantity() <= 0) {
            throw new ValidationItemQuantityException("Quantity must be greater than 0");
        }
        OrderEntity order = orderRetriever.getOrderEntity(orderId);
        MenuItemDto menuItem = menuFacade.getMenuItem(request.itemId());

        OrderItemEntity existing = order.getItems().stream()
                .filter(item -> item.getName().equals(menuItem.name()))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + request.quantity());

            String newNote = request.note();
            if (newNote != null) {
                if (existing.getNote() == null) {
                    existing.setNote(newNote);
                } else {
                    existing.setNote(existing.getNote() + "\n" + newNote);
                }
            }
        } else {
            OrderItemEntity newItem = new OrderItemEntity();
            newItem.setName(menuItem.name());
            newItem.setPrice(menuItem.price());
            newItem.setQuantity(request.quantity());
            newItem.setNote(request.note());

            order.addItem(newItem);
        }

        recalculateTotalPrice(order);

        return orderEntityMapper.toOrderEntityDto(order);
    }

    @Transactional
    OrderDto removeItem(final Long orderId, final Long itemId) {
        OrderEntity order = orderRetriever.getOrderEntity(orderId);
        OrderItemEntity orderItemEntity = order.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new OrderItemNotFoundException(orderId, itemId));
        order.removeItem(orderItemEntity);

        recalculateTotalPrice(order);

        return orderEntityMapper.toOrderEntityDto(order);
    }

    private static void recalculateTotalPrice(final OrderEntity order) {
        BigDecimal total = order.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalPrice(total);
    }
}
