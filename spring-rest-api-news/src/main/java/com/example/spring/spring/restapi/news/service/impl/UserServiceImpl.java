package com.example.spring.spring.restapi.news.service.impl;

import com.example.spring.spring.restapi.news.mapper.UserMapper;
import com.example.spring.spring.restapi.news.model.User;
import com.example.spring.spring.restapi.news.repository.AggregateRepository;
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

    private final AggregateRepository aggregateRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponse> findAll() {
        return userMapper.toResponseList(getRepository().findAll());
    }

    @Override
    public UserResponse findById(Long id) {
        return userMapper.toResponse(aggregateRepository.getUserOrFail(id));
    }

    @Override
    public UserResponse create(UserRequest request) {
        User user = userMapper.toModel(request);
        return userMapper.toResponse(getRepository().save(user));
    }

    @Override
    public UserResponse update(Long userId, UserRequest request) {
        aggregateRepository.getUserOrFail(userId);
        User updatedUser = userMapper.toModel(userId, request);
        return userMapper.toResponse(getRepository().save(updatedUser));
    }

    @Override
    public void deleteById(Long id) {
        getRepository().deleteById(id);
    }

    private UserRepository getRepository() {
        return aggregateRepository.getUserRepository();
    }

}
