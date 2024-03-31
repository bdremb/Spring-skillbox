package com.example.spring.spring.restapi.news.service.impl;

import com.example.spring.spring.restapi.news.mapper.CommentMapper;
import com.example.spring.spring.restapi.news.model.Comment;
import com.example.spring.spring.restapi.news.model.NewsItem;
import com.example.spring.spring.restapi.news.model.User;
import com.example.spring.spring.restapi.news.repository.AggregateRepository;
import com.example.spring.spring.restapi.news.repository.CommentRepository;
import com.example.spring.spring.restapi.news.repository.specification.CommentSpecification;
import com.example.spring.spring.restapi.news.service.CommentService;
import com.example.spring.spring.restapi.news.web.model.request.CommentRequest;
import com.example.spring.spring.restapi.news.web.model.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final AggregateRepository aggregateRepository;
    private final CommentMapper commentMapper;

    @Override
    public List<CommentResponse> findAllByNewsItemId(Long newsItemId) {
        final List<Comment> list = getRepository().findAll(CommentSpecification.withFilterByNewsItemId(newsItemId));
        return commentMapper.toResponseList(list);
    }

    @Override
    public Long countByNewsId(Long newsItemId) {
        return getRepository().countByNewsItemId(newsItemId);
    }

    @Override
    public CommentResponse findById(Long id) {
        return commentMapper.toResponse(aggregateRepository.getCommentOrFail(id));
    }

    @Override
    public CommentResponse update(Long id, CommentRequest request) {
        aggregateRepository.getNewsItemOrFail(request.getNewsItemId());
        final Comment updatedComment = commentMapper.toUpdateModel(aggregateRepository.getCommentOrFail(id), request);
        updatedComment.setCommentText(request.getCommentText());

        return commentMapper.toResponse(getRepository().save(updatedComment));
    }

    @Override
    public void deleteById(Long id) {
        getRepository().deleteById(id);
    }

    @Override
    public CommentResponse create(String userName, CommentRequest request) {
        final User user = aggregateRepository.getUserOrFail(userName);
        final NewsItem newsItem = aggregateRepository.getNewsItemOrFail(request.getNewsItemId());
        final Comment comment = commentMapper.toModel(user, newsItem, request);
        return commentMapper.toResponse(getRepository().save(comment));
    }

    private CommentRepository getRepository() {
        return aggregateRepository.getCommentRepository();
    }

}
