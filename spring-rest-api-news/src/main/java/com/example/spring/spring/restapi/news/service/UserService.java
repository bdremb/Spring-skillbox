package com.example.spring.spring.restapi.news.service;

import com.example.spring.spring.restapi.news.model.RoleType;
import com.example.spring.spring.restapi.news.model.User;
import com.example.spring.spring.restapi.news.web.model.request.UserRequest;
import com.example.spring.spring.restapi.news.web.model.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> findAll();

    UserResponse findById(Long id);

    UserResponse create(UserRequest request, RoleType roleType);

    UserResponse update(Long userId, UserRequest request);

    void deleteById(Long id);

    User getUserOrFail(Long id);

    User findByUserByName(String userName);
}
