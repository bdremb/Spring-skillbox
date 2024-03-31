package com.example.spring.spring.restapi.news.mapper;

import com.example.spring.spring.restapi.news.model.NewsItem;
import com.example.spring.spring.restapi.news.repository.AggregateRepository;
import com.example.spring.spring.restapi.news.web.model.request.NewsItemRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class NewsItemMapperDelegate implements NewsItemMapper{

    private final AggregateRepository commonRepository;

    @Override
    public NewsItem toModel(String userName, NewsItemRequest request) {
        return NewsItem.builder()
                .text(request.getText())
                .user(commonRepository.getUserOrFail(userName))
                .category(commonRepository.getCategoryOrFail(request.getCategoryName()))
                .build();
    }

    @Override
    public NewsItem toUpdateModel(NewsItem newsItem, NewsItemRequest request) {
        if (newsItem == null) {
            return null;
        }
        newsItem.setCategory(commonRepository.getCategoryOrFail(request.getCategoryName()));
        return newsItem;
    }

}
