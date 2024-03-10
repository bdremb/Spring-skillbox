package com.example.spring.spring.restapi.news.service.impl;

import com.example.spring.spring.restapi.news.model.NewsCategory;
import com.example.spring.spring.restapi.news.repository.NewsCategoryRepository;
import com.example.spring.spring.restapi.news.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final NewsCategoryRepository categoryRepository;
    @Override
    public List<NewsCategory> findAll() {
        return null;
    }

    @Override
    public NewsCategory findById(Long id) {
        return null;
    }

    @Override
    public NewsCategory save(NewsCategory client) {
        return null;
    }

    @Override
    public NewsCategory update(NewsCategory client) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
