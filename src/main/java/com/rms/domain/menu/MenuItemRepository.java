package com.rms.domain.menu;

import org.springframework.data.jpa.repository.JpaRepository;

interface MenuItemRepository extends JpaRepository<MenuItemEntity, Long> {
}