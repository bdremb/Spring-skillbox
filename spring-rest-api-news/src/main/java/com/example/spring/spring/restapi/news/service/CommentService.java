package com.example.spring.spring.restapi.news.service;

import com.example.spring.spring.restapi.news.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> findAll();

    Long countByNewsId(Long newsId);

    Comment findById(Long id);

    Comment save(Comment client);

    Comment update(Comment client);

    void deleteById(Long id);
}
