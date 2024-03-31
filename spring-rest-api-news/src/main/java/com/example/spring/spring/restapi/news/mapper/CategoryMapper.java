package com.example.spring.spring.restapi.news.mapper;

import com.example.spring.spring.restapi.news.model.Category;
import com.example.spring.spring.restapi.news.web.model.request.CategoryRequest;
import com.example.spring.spring.restapi.news.web.model.response.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {NewsItemMapper.class}
)
public interface CategoryMapper {

    Category toModel(CategoryRequest request);

    Category toModel(Long id, CategoryRequest request);

    CategoryResponse toResponse(Category model);

    List<CategoryResponse> toResponseList(List<Category> models);

}
