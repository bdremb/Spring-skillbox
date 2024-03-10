package com.example.spring.spring.restapi.news.repository;


import com.example.spring.spring.restapi.news.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(Long id);

    User save(User client);

    User update(User client);

    void deleteById(Long id);
}
