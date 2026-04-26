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
                .orElseThrow(() -> new OrderNotFoundException(idOrder));
        return orderEntityMapper.toOrderEntityDto(orderEntity);
    }

    OrderEntity getOrderEntity(Long idOrder) {
        return orderRepository.findById(idOrder)
                .orElseThrow(() -> new OrderNotFoundException(idOrder));
    }
}
