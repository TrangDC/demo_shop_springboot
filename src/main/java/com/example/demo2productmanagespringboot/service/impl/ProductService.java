package com.example.demo2productmanagespringboot.service.impl;

import com.example.demo2productmanagespringboot.model.Category;
import com.example.demo2productmanagespringboot.model.Product;
import com.example.demo2productmanagespringboot.repository.IProductRepository;
import com.example.demo2productmanagespringboot.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository iProductRepository;
    @Override
    public Iterable<Product> findAll() {
        return iProductRepository.findAll();
    }

    @Override
    public void save(Product product) {
        iProductRepository.save(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return iProductRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        iProductRepository.deleteById(id);
    }

    @Override
    public Page<Product> findAllPage(Pageable pageable) {
        return iProductRepository.findAll(pageable);
    }

    @Override
    public Iterable<Product> searchByWord(String word) {
        return iProductRepository.findByNameContaining(word);
    }

    @Override
    public Page<Product> searchByWord(String word, Pageable pageable) {
        return iProductRepository.findAllByNameContaining(word, pageable);
    }

    @Override
    public Iterable<Product> sortPriceAscending() {
        return iProductRepository.findAllByOrderByPriceAsc();
    }

    @Override
    public Iterable<Product> sortPriceDescending() {
        return iProductRepository.findAllByOrderByPriceDesc();
    }

    @Override
    public Iterable<Product> findByCategory(Category category) {
        return iProductRepository.findAllByCategory(category);
    }

    public Page<Product> sortPriceAscending(Pageable pageable) {
        return iProductRepository.findAllByOrderByPriceAsc(pageable);
    }
    public Page<Product> sortPriceDescending(Pageable pageable) {
        return iProductRepository.findAllByOrderByPriceDesc(pageable);
    }
}
