package com.rms.domain.order;

import com.rms.domain.order.dto.OrderCreateRequestDto;
import com.rms.domain.order.dto.OrderCreateResponseDto;
import com.rms.domain.order.dto.OrderDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderFacade {

    private final OrderRetriever orderRetriever;
    private final OrderCreator orderCreator;

    public OrderDto getById(Long id) {
        return orderRetriever.getOrder(id);
    }

    public OrderCreateResponseDto createOrder(OrderCreateRequestDto requestDto) {
        return orderCreator.createOrder(requestDto);
    }
}