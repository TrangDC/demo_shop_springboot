package com.example.demo2productmanagespringboot.repository;

import com.example.demo2productmanagespringboot.model.Category;
import com.example.demo2productmanagespringboot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ICategoryRepository extends PagingAndSortingRepository<Category, Long>, JpaRepository<Category,Long> {
}
