package com.example.spring.spring.restapi.news.repository;


import com.example.spring.spring.restapi.news.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"newsItems", "roles"})
    Optional<User> findByName(String name);

}
