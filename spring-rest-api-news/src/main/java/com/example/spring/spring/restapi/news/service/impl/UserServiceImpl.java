package com.example.spring.spring.restapi.news.service.impl;

import com.example.spring.spring.restapi.news.mapper.UserMapper;
import com.example.spring.spring.restapi.news.model.User;
import com.example.spring.spring.restapi.news.repository.AggregateRepository;
import com.example.spring.spring.restapi.news.service.UserService;
import com.example.spring.spring.restapi.news.web.model.request.UserRequest;
import com.example.spring.spring.restapi.news.web.model.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends AbstractService implements UserService {

    private final AggregateRepository aggregateRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponse> findAll() {
        return userMapper.toResponseList(aggregateRepository.getUserRepository().findAll());
    }

    @Override
    public UserResponse findById(Long id) {
        return userMapper.toResponse(getUserOrFail(id));
    }

    @Override
    public UserResponse create(UserRequest request) {
        return userMapper.toResponse(aggregateRepository.getUserRepository().save(userMapper.toModel(request)));
    }

    @Override
    public UserResponse update(Long userId, UserRequest request) {
        User updatedUser = userMapper.toUpdatedModel(getUserOrFail(userId), request);
        return userMapper.toResponse(aggregateRepository.getUserRepository().save(updatedUser));
    }

    @Override
    public void deleteById(Long id) {
        aggregateRepository.getUserRepository().deleteById(id);
    }

    @Override
    public AggregateRepository getAggregateRepository() {
        return aggregateRepository;
    }
}
