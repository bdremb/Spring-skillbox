package com.example.spring.spring.restapi.news.service.impl;

import com.example.spring.spring.restapi.news.mapper.CategoryMapper;
import com.example.spring.spring.restapi.news.repository.CategoryRepository;
import com.example.spring.spring.restapi.news.service.CategoryService;
import com.example.spring.spring.restapi.news.web.model.request.CategoryRequest;
import com.example.spring.spring.restapi.news.web.model.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> findAll() {
        return categoryMapper.toResponseList(categoryRepository.findAll());
    }

    @Override
    public CategoryResponse findById(Long id) {
        return null;
    }

    @Override
    public CategoryResponse findByName(String categoryName) {
        return null;
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {
        return null;
    }

    @Override
    public CategoryResponse update(Long categoryId, CategoryRequest request) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
