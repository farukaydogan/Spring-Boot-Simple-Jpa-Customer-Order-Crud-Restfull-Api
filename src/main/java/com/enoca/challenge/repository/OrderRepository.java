package com.enoca.challenge.repository;

import com.enoca.challenge.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCreateDateAfter(Date date);
}