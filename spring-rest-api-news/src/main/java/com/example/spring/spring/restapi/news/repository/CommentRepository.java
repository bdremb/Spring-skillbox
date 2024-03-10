package com.example.spring.spring.restapi.news.repository;


import com.example.spring.spring.restapi.news.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Long countByNewsItemId(Long newsItemId);
}

