package com.example.spring.spring.restapi.news.service.impl;

import com.example.spring.spring.restapi.news.model.Comment;
import com.example.spring.spring.restapi.news.repository.CommentRepository;
import com.example.spring.spring.restapi.news.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public List<Comment> findAll() {
        return null;
    }

    @Override
    public Comment findById(Long id) {
        return null;
    }

    @Override
    public Comment save(Comment client) {
        return null;
    }

    @Override
    public Comment update(Comment client) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
