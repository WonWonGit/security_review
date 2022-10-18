package com.example.security_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security_test.domain.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    public User findByUsername(String username);
}
