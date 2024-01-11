package com.example.demo2productmanagespringboot.repository;

import com.example.demo2productmanagespringboot.model.Order;
import com.example.demo2productmanagespringboot.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
