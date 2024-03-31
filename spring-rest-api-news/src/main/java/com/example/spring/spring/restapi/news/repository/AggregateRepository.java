package com.example.spring.spring.restapi.news.repository;

import com.example.spring.spring.restapi.news.exception.EntityNotFoundException;
import com.example.spring.spring.restapi.news.model.Category;
import com.example.spring.spring.restapi.news.model.Comment;
import com.example.spring.spring.restapi.news.model.NewsItem;
import com.example.spring.spring.restapi.news.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;


@Component
@Getter
@RequiredArgsConstructor
public class AggregateRepository {

    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final NewsItemRepository newsItemRepository;
    private final UserRepository userRepository;

    public Category getCategoryOrFail(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Category with id={0} not found", id)));
    }

    public Category getCategoryOrFail(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new EntityNotFoundException(format("Category with name={0} not found", categoryName)));
    }

    public Comment getCommentOrFail(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Comment with id={0} not found", id)));
    }

    public NewsItem getNewsItemOrFail(Long id) {
        return newsItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("NewsItem with id={0} not found", id)));
    }

    public User getUserOrFail(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("User with id={0} not found", id)));
    }

    public User getUserOrFail(String userName) {
        return userRepository.findByName(userName)
                .orElseThrow(() -> new EntityNotFoundException(format("User with name={0} not found", userName)));
    }

}
