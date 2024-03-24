package com.example.spring.spring.restapi.news.service;

import com.example.spring.spring.restapi.news.web.model.request.NewsFilterRequest;
import com.example.spring.spring.restapi.news.web.model.request.NewsItemRequest;
import com.example.spring.spring.restapi.news.web.model.response.NewsItemResponse;

import java.util.List;

public interface NewsService {
    List<NewsItemResponse> findAll(NewsFilterRequest filter);

    NewsItemResponse findById(Long id);

    NewsItemResponse create(String userName, NewsItemRequest client);

    NewsItemResponse update(Long id,  NewsItemRequest client);

    void deleteById(Long id);
}
