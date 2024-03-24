package com.example.spring.spring.restapi.news.service;

import com.example.spring.spring.restapi.news.model.User;
import com.example.spring.spring.restapi.news.web.model.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> findAll();

    UserResponse findById(Long id);

    UserResponse findByName(String userName);

    UserResponse save(User user);

    UserResponse update(User user);

    void deleteById(Long id);
}
