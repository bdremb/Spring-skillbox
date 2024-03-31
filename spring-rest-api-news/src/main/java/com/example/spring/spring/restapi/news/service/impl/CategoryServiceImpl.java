package com.example.spring.spring.restapi.news.service.impl;

import com.example.spring.spring.restapi.news.mapper.CategoryMapper;
import com.example.spring.spring.restapi.news.model.Category;
import com.example.spring.spring.restapi.news.repository.AggregateRepository;
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

    private final AggregateRepository aggregateRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> findAll() {
        return categoryMapper.toResponseList(getRepository().findAll());
    }

    @Override
    public CategoryResponse findById(Long id) {
        return categoryMapper.toResponse(aggregateRepository.getCategoryOrFail(id));
    }

//    @Override
//    public CategoryResponse findByName(String categoryName) {
//        return categoryMapper.toResponse(
//                categoryRepository.findByCategoryName(categoryName)
//                        .orElseThrow(() -> new EntityNotFoundException(format("Category with name={0} not found", categoryName)))
//        );
//    }

    @Override
    public CategoryResponse create(CategoryRequest request) {
        return categoryMapper.toResponse(getRepository().save(categoryMapper.toModel(request)));
    }

    @Override
    public CategoryResponse update(Long categoryId, CategoryRequest request) {
        aggregateRepository.getCategoryOrFail(categoryId);
        Category updatedCategory = categoryMapper.toModel(categoryId, request);
        return categoryMapper.toResponse(getRepository().save(updatedCategory));
    }

    @Override
    public void deleteById(Long id) {
        getRepository().deleteById(id);
    }

    private CategoryRepository getRepository() {
        return aggregateRepository.getCategoryRepository();
    }

}
