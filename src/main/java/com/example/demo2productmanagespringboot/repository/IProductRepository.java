package com.example.demo2productmanagespringboot.repository;

import com.example.demo2productmanagespringboot.model.Category;
import com.example.demo2productmanagespringboot.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IProductRepository extends PagingAndSortingRepository<Product, Long>, JpaRepository<Product,Long> {
    public Iterable<Product> findAllByCategory(Category category);

    public Iterable<Product> findByNameContaining(String word);

    public Iterable<Product> findAllByOrderByPriceAsc();
    public Iterable<Product> findAllByOrderByPriceDesc();

    Page<Product> findAllByNameContaining(String word, Pageable pageable);

    Page<Product> findAllByOrderByPriceAsc(Pageable pageable);
    Page<Product> findAllByOrderByPriceDesc(Pageable pageable);
}
