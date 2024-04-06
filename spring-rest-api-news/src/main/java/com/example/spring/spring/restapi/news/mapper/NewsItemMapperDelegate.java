package com.example.spring.spring.restapi.news.mapper;

import com.example.spring.spring.restapi.news.model.NewsItem;
import com.example.spring.spring.restapi.news.service.CategoryService;
import com.example.spring.spring.restapi.news.service.CommentService;
import com.example.spring.spring.restapi.news.service.NewsService;
import com.example.spring.spring.restapi.news.web.model.request.NewsItemRequest;
import com.example.spring.spring.restapi.news.web.model.response.NewsItemResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public abstract class NewsItemMapperDelegate implements NewsItemMapper {

    @Autowired
    private NewsService newsService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CommentService commentService;

    @Override
    public NewsItem toModel(String userName, NewsItemRequest request) {
        return NewsItem.builder()
                .text(request.getText())
                .user(newsService.getUserOrFail(userName))
                .category(categoryService.getCategoryOrFail(request.getCategoryName()))
                .build();
    }

    @Override
    public NewsItem toUpdateModel(NewsItem newsItem, NewsItemRequest request) {
        if (newsItem == null) {
            return null;
        }
        newsItem.setCategory(categoryService.getCategoryOrFail(request.getCategoryName()));
        return newsItem;
    }

    @Override
    public NewsItemResponse toResponse(NewsItem model) {
         NewsItemResponse  response = toSimpleResponse(model);
         response.setComments(commentService.findAllByNewsItemId(model.getId()));
         return response;
    }

    @Override
    public List<NewsItemResponse> toResponseList(List<NewsItem> models) {
        return models.stream().map(this::toSimpleResponse).toList();
    }

    private NewsItemResponse toSimpleResponse(NewsItem model) {
        NewsItemResponse  response = new NewsItemResponse();
        response.setUserName(model.getUser().getName());
        response.setId(model.getId());
        response.setText(model.getText());
        response.setCreatedAt(model.getCreatedAt());
        response.setUpdatedAt(model.getUpdatedAt());
        response.setCategoryName(model.getCategory().getCategoryName());
        response.setCommentsCount(commentService.countByNewsId(model.getId()));
        return response;
    }

}
