package com.example.spring.spring.restapi.news.repository;


import com.example.spring.spring.restapi.news.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"newsItems"})
    Optional<User> findByUsername(String username);

    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByIdAndUsername(Long id, String username);

    @EntityGraph(attributePaths = {"roles"})
    List<User> findAll();

}
