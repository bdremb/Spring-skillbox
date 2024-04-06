package com.example.spring.spring.restapi.news.service.impl;

import com.example.spring.spring.restapi.news.mapper.CategoryMapper;
import com.example.spring.spring.restapi.news.model.Category;
import com.example.spring.spring.restapi.news.repository.AggregateRepository;
import com.example.spring.spring.restapi.news.service.CategoryService;
import com.example.spring.spring.restapi.news.web.model.request.CategoryRequest;
import com.example.spring.spring.restapi.news.web.model.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends AbstractService implements CategoryService {

    private final AggregateRepository aggregateRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> findAll() {
        return categoryMapper.toResponseList(aggregateRepository.getCategoryRepository().findAll());
    }

    @Override
    public CategoryResponse findById(Long id) {
        return categoryMapper.toResponse(getCategoryOrFail(id));
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {
        final Category category = aggregateRepository.getCategoryRepository().save(categoryMapper.toModel(request));
        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse update(Long categoryId, CategoryRequest request) {
        Category updatedCategory = categoryMapper.toUpdatedModel(getCategoryOrFail(categoryId), request);
        return categoryMapper.toResponse(aggregateRepository.getCategoryRepository().save(updatedCategory));
    }

    @Override
    public void deleteById(Long id) {
        aggregateRepository.getCategoryRepository().deleteById(id);
    }

    @Override
    public AggregateRepository getAggregateRepository() {
        return aggregateRepository;
    }
}
