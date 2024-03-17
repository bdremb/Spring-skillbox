package com.example.spring.spring.restapi.news.service;

import com.example.spring.spring.restapi.news.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long id);

    User findByName(String userName);

    User save(User user);

    User update(User user);

    void deleteById(Long id);
}
