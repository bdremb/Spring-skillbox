package com.example.spring.spring.restapi.news.service;

import com.example.spring.spring.restapi.news.web.model.request.CommentRequest;
import com.example.spring.spring.restapi.news.web.model.response.CommentResponse;

import java.util.List;

public interface CommentService {

    List<CommentResponse> findAllByNewsItemId(Long newsItemId);

    Long countByNewsId(Long newsId);

    CommentResponse findById(Long id);

    CommentResponse update(Long id, CommentRequest request);

    void deleteById(Long id);

    CommentResponse create(String userName, CommentRequest request);
}
