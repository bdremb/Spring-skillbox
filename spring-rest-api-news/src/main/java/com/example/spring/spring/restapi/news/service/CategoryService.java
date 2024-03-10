package com.example.spring.spring.restapi.news.service;

import com.example.spring.spring.restapi.news.model.NewsCategory;

import java.util.List;

public interface CategoryService {

    List<NewsCategory> findAll();

    NewsCategory findById(Long id);

    NewsCategory save(NewsCategory client);

    NewsCategory update(NewsCategory client);

    void deleteById(Long id);
}
