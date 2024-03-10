package com.example.spring.spring.restapi.news.service;

import com.example.spring.spring.restapi.news.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long id);

    User save(User client);

    User update(User client);

    void deleteById(Long id);
}
