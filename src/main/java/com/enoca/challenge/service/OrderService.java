
package com.enoca.challenge.service;

import com.enoca.challenge.model.Order;
import com.enoca.challenge.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor

public class OrderService {


    private final OrderRepository orderRepository;


    public List<Order> getOrdersAfterDate(Date date) {
        return orderRepository.findByCreateDateAfter(date);
    }
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID " + id + " olan sipariş bulunamadı.");
        }
        orderRepository.deleteById(id);
    }

}
