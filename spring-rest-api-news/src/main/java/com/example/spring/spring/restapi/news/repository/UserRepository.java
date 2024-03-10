package com.example.spring.spring.restapi.news.repository;


import com.example.spring.spring.restapi.news.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


}
