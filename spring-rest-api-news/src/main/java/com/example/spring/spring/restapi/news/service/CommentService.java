package com.example.spring.spring.restapi.news.service;

import com.example.spring.spring.restapi.news.model.Comment;
import com.example.spring.spring.restapi.news.web.model.response.CommentResponse;

import java.util.List;

public interface CommentService {

    List<CommentResponse> findAll();

    Long countByNewsId(Long newsId);

    CommentResponse findById(Long id);

    CommentResponse save(Comment client);

    CommentResponse update(Comment client);

    void deleteById(Long id);
}
