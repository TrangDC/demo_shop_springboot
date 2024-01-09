package com.example.demo2productmanagespringboot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IGenerateService<T> {
    public Iterable<T> findAll();
    public void save(T t);
    Optional<T> findById(Long id);
    public void remove(Long id);
    Page<T> findAllPage(Pageable pageable);
}
