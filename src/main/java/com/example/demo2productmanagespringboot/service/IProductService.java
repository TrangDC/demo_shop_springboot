package com.example.demo2productmanagespringboot.service;

import com.example.demo2productmanagespringboot.model.Category;
import com.example.demo2productmanagespringboot.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService extends IGenerateService<Product> {
    public Iterable<Product> findByCategory(Category category);

    public Iterable<Product> searchByWord(String word);

    public Page<Product> searchByWord(String word, Pageable pageable);

    public Iterable<Product> sortPriceAscending();
    public Iterable<Product> sortPriceDescending();

    public Page<Product> sortPriceAscending(Pageable pageable);
    public Page<Product> sortPriceDescending(Pageable pageable);
}
