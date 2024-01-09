package com.example.demo2productmanagespringboot.service.impl;

import com.example.demo2productmanagespringboot.model.Category;
import com.example.demo2productmanagespringboot.repository.ICategoryRepository;
import com.example.demo2productmanagespringboot.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private ICategoryRepository iCategoryRepository;
    @Override
    public Iterable<Category> findAll() {
        return iCategoryRepository.findAll();
    }

    @Override
    public void save(Category category) {
        iCategoryRepository.save(category);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return iCategoryRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        iCategoryRepository.deleteById(id);
    }

    @Override
    public Page<Category> findAllPage(Pageable pageable) {
        return iCategoryRepository.findAll(pageable);
    }
}
