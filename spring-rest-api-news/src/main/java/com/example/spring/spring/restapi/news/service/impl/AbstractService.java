package com.example.spring.spring.restapi.news.service.impl;

import com.example.spring.spring.restapi.news.exception.EntityNotFoundException;
import com.example.spring.spring.restapi.news.model.Category;
import com.example.spring.spring.restapi.news.model.Comment;
import com.example.spring.spring.restapi.news.model.NewsItem;
import com.example.spring.spring.restapi.news.model.User;
import com.example.spring.spring.restapi.news.repository.AggregateRepository;

import static java.text.MessageFormat.format;

public abstract class AbstractService {

    public Category getCategoryOrFail(Long id) {
        return getAggregateRepository().getCategoryRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Category with id={0} not found", id)));
    }

    public Category getCategoryOrFail(String categoryName) {
        return getAggregateRepository().getCategoryRepository().findByCategoryName(categoryName)
                .orElseThrow(() -> new EntityNotFoundException(format("Category with name={0} not found", categoryName)));
    }

    public Comment getCommentOrFail(Long id) {
        return getAggregateRepository().getCommentRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Comment with id={0} not found", id)));
    }

    public NewsItem getNewsItemOrFail(Long id) {
        return getAggregateRepository().getNewsItemRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("NewsItem with id={0} not found", id)));
    }

    public User getUserOrFail(Long id) {
        return getAggregateRepository().getUserRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("User with id={0} not found", id)));
    }

    public User getUserOrFail(String userName) {
        return getAggregateRepository().getUserRepository().findByName(userName)
                .orElseThrow(() -> new EntityNotFoundException(format("User with name={0} not found", userName)));
    }

    public abstract AggregateRepository getAggregateRepository();
}
