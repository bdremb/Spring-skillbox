package com.example.spring.spring.restapi.news.service;

import com.example.spring.spring.restapi.news.model.NewsItem;

import java.util.List;

public interface NewsService {
    List<NewsItem> findAll();

    NewsItem findById(Long id);

    NewsItem save(NewsItem client);

    NewsItem update(NewsItem client);

    void deleteById(Long id);
}
