package com.example.spring.spring.restapi.news.service.impl;

import com.example.spring.spring.restapi.news.mapper.UserMapper;
import com.example.spring.spring.restapi.news.model.User;
import com.example.spring.spring.restapi.news.repository.UserRepository;
import com.example.spring.spring.restapi.news.service.UserService;
import com.example.spring.spring.restapi.news.web.model.request.UserRequest;
import com.example.spring.spring.restapi.news.web.model.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public List<UserResponse> findAll() {
        return userMapper.toResponseList(userRepository.findAll());
    }

    @Override
    public UserResponse findById(Long id) {
        return null;
    }

    @Override
    public UserResponse findByName(String userName) {
        return null;
    }

    @Override
    public UserResponse create(UserRequest request) {
        User user = userMapper.toModel(request);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse update(Long userId, UserRequest request) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
