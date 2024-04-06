package com.example.spring.spring.restapi.news.mapper;

import com.example.spring.spring.restapi.news.model.NewsItem;
import com.example.spring.spring.restapi.news.web.model.request.NewsItemRequest;
import com.example.spring.spring.restapi.news.web.model.response.NewsItemResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@DecoratedWith(NewsItemMapperDelegate.class)
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CommentMapper.class, UserMapper.class}
)
public interface NewsItemMapper {

    NewsItem toModel(String userName, NewsItemRequest request);

    NewsItem toModel(Long id, NewsItemRequest request);

    NewsItemResponse toResponse(NewsItem model);

    @Mapping(target = "text", source = "request.text")
    NewsItem toUpdateModel(NewsItem newsItem, NewsItemRequest request);

    List<NewsItemResponse> toResponseList(List<NewsItem> models);

}
