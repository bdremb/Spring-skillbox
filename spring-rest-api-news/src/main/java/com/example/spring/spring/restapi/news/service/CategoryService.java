package com.example.spring.spring.restapi.news.service;

import com.example.spring.spring.restapi.news.web.model.request.CategoryRequest;
import com.example.spring.spring.restapi.news.web.model.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> findAll();

    CategoryResponse findById(Long id);

    CategoryResponse findByName(String categoryName);

    CategoryResponse create(CategoryRequest request);

    CategoryResponse update(Long categoryId, CategoryRequest request);

    void deleteById(Long id);
}
