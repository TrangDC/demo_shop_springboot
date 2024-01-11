package com.example.demo2productmanagespringboot.service.impl;

import com.example.demo2productmanagespringboot.model.User;
import com.example.demo2productmanagespringboot.repository.IUserRepository;
import com.example.demo2productmanagespringboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository iUserRepository;

    @Override
    public Iterable<User> findAll() {
        return iUserRepository.findAll();
    }

    @Override
    public void save(User user) {
        iUserRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return iUserRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        iUserRepository.deleteById(id);
    }

    @Override
    public Page<User> findAllPage(Pageable pageable) {
        return iUserRepository.findAll(pageable);
    }
}
