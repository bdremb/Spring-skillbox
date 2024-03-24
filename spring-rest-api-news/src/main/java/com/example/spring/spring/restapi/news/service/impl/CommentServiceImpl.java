package com.example.spring.spring.restapi.news.service.impl;

import com.example.spring.spring.restapi.news.exception.EntityNotFoundException;
import com.example.spring.spring.restapi.news.mapper.CommentMapper;
import com.example.spring.spring.restapi.news.model.Comment;
import com.example.spring.spring.restapi.news.repository.CommentRepository;
import com.example.spring.spring.restapi.news.service.CommentService;
import com.example.spring.spring.restapi.news.web.model.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public List<CommentResponse> findAll() {
        return commentMapper.toResponseList(commentRepository.findAll());
    }

    @Override
    public Long countByNewsId(Long newsId) {
        return commentRepository.countByNewsItemId(newsId);
    }

    @Override
    public CommentResponse findById(Long id) {
        return commentMapper.toResponse(getCommentOrFail(id));
    }

    @Override
    public CommentResponse save(Comment client) {
        return null;
    }

    @Override
    public CommentResponse update(Comment client) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    private Comment getCommentOrFail(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("NewsItem with id={0} not found", id)));
    }
}
