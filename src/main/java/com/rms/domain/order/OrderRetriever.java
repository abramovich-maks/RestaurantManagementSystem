package com.rms.domain.order;

import com.rms.domain.order.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OrderRetriever {

    private final OrderRepository orderRepository;

    private final OrderEntityMapper orderEntityMapper;

    OrderDto getOrder(Long idOrder) {
        OrderEntity orderEntity = orderRepository.findById(idOrder)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + idOrder));
        return orderEntityMapper.toOrderEntityDto(orderEntity);
    }
}
