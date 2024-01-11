package com.example.demo2productmanagespringboot.service.impl;

import com.example.demo2productmanagespringboot.model.Order;
import com.example.demo2productmanagespringboot.repository.IOrderRepository;
import com.example.demo2productmanagespringboot.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository orderRepository;

    @Override
    public Iterable<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Page<Order> findAllPage(Pageable pageable) {
        return null;
    }
}
