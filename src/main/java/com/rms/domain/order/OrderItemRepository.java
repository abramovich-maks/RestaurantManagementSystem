package com.rms.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;

interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
}