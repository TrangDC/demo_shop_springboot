package com.example.demo2productmanagespringboot.repository;

import com.example.demo2productmanagespringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
}
