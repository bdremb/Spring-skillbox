package com.example.spring.spring.restapi.news.service.impl;

import com.example.spring.spring.restapi.news.model.User;
import com.example.spring.spring.restapi.news.repository.UserRepository;
import com.example.spring.spring.restapi.news.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public User save(User client) {
        return null;
    }

    @Override
    public User update(User client) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
