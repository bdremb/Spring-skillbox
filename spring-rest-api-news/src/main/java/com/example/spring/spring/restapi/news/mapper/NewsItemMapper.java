package com.example.spring.spring.restapi.news.mapper;

import com.example.spring.spring.restapi.news.model.Category;
import com.example.spring.spring.restapi.news.model.NewsItem;
import com.example.spring.spring.restapi.news.web.model.request.NewsItemRequest;
import com.example.spring.spring.restapi.news.web.model.response.NewsItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static java.util.Objects.nonNull;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CommentMapper.class, UserMapper.class}
)
public interface NewsItemMapper {

    NewsItem toModel(NewsItemRequest request);

    NewsItem toModel(Long id, NewsItemRequest request);

    NewsItemResponse toResponse(NewsItem model);

    default NewsItem toUpdateModel(NewsItem newsItem, Category category, NewsItemRequest request) {
        return NewsItem.builder()
                .id(newsItem.getId())
                .text(nonNull(request.getText()) ? request.getText() : newsItem.getText())
                .user(newsItem.getUser())
                .category(nonNull(category) ? category : newsItem.getCategory())
                .comments(newsItem.getComments())
                .commentsCount(newsItem.getCommentsCount())
                .build();
    }

    List<NewsItemResponse> toResponseList(List<NewsItem> models);

}
