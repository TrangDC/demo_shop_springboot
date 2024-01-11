package com.example.demo2productmanagespringboot.repository;

import com.example.demo2productmanagespringboot.model.Order;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface IOrderRepository extends JpaRepository<Order, Long> {
}
