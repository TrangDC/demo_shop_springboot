package com.example.demo2productmanagespringboot.service.impl;

import com.example.demo2productmanagespringboot.model.OrderDetail;
import com.example.demo2productmanagespringboot.repository.IOrderDetailRepository;
import com.example.demo2productmanagespringboot.service.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderDetailService implements IOrderDetailService {

    @Autowired
    private IOrderDetailRepository orderDetailRepository;
    @Override
    public Iterable<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

    @Override
    public void save(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }

    @Override
    public Optional<OrderDetail> findById(Long id) {
        return orderDetailRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public Page<OrderDetail> findAllPage(Pageable pageable) {
        return orderDetailRepository.findAll(pageable);
    }
}
