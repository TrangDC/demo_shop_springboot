//package com.example.demo2productmanagespringboot.service.impl;
//
//import com.example.demo2productmanagespringboot.model.Cart;
//import com.example.demo2productmanagespringboot.repository.ICartRepository;
//import com.example.demo2productmanagespringboot.repository.ICategoryRepository;
//import com.example.demo2productmanagespringboot.service.ICartService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//@Service
//public class CartService implements ICartService {
//
//    @Autowired
//    private ICartRepository iCategoryRepository;
//    @Override
//    public Iterable<Cart> findAll() {
//        return iCategoryRepository.findAll();
//    }
//
//    @Override
//    public void save(Cart cart) {
//        iCategoryRepository.save(cart);
//    }
//
//    @Override
//    public Optional<Cart> findById(Long id) {
//        return iCategoryRepository.findById(id);
//    }
//
//    @Override
//    public void remove(Long id) {
//        iCategoryRepository.deleteById(id);
//    }
//
//    @Override
//    public Page<Cart> findAllPage(Pageable pageable) {
//        return null;
//    }
//}
