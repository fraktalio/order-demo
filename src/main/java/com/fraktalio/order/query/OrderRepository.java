package com.fraktalio.order.query;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface OrderRepository extends JpaRepository<OrderEntity, String> {

    List<OrderEntity> findAllByUserId(String userId);
}
