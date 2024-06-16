package com.example.spring.spring.restapi.news.repository;


import com.example.spring.spring.restapi.news.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {
    Long countByNewsItemId(Long newsItemId);

    Optional<Comment> findByIdAndUserId(Long id, Long userId);
}

