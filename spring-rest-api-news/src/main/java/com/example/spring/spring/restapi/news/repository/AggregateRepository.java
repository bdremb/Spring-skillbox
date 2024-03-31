package com.example.spring.spring.restapi.news.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@Getter
@RequiredArgsConstructor
public class AggregateRepository {

    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final NewsItemRepository newsItemRepository;
    private final UserRepository userRepository;
}
