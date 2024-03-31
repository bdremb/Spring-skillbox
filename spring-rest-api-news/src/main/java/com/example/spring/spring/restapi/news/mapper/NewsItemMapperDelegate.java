package com.example.spring.spring.restapi.news.mapper;

import com.example.spring.spring.restapi.news.model.NewsItem;
import com.example.spring.spring.restapi.news.service.CommentService;
import com.example.spring.spring.restapi.news.service.impl.NewsServiceImpl;
import com.example.spring.spring.restapi.news.web.model.request.NewsItemRequest;
import com.example.spring.spring.restapi.news.web.model.response.NewsItemResponse;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class NewsItemMapperDelegate implements NewsItemMapper {

    @Autowired
    private NewsServiceImpl newsService;
    @Autowired
    private CommentService commentService;

    @Override
    public NewsItem toModel(String userName, NewsItemRequest request) {
        return NewsItem.builder()
                .text(request.getText())
                .user(newsService.getUserOrFail(userName))
                .category(newsService.getCategoryOrFail(request.getCategoryName()))
                .build();
    }

    @Override
    public NewsItem toUpdateModel(NewsItem newsItem, NewsItemRequest request) {
        if (newsItem == null) {
            return null;
        }
        newsItem.setCategory(newsService.getCategoryOrFail(request.getCategoryName()));
        return newsItem;
    }

    @Override
    public NewsItemResponse toResponse(NewsItem model) {
         NewsItemResponse  response = new NewsItemResponse();
         response.setUserName(model.getUser().getName());
         response.setId(model.getId());
         response.setText(model.getText());
         response.setComments(commentService.findAllByNewsItemId(model.getId()));
         response.setCreatedAt(model.getCreatedAt());
         response.setUpdatedAt(model.getUpdatedAt());
         response.setCategoryName(model.getCategory().getCategoryName());
         response.setCommentsCount(commentService.countByNewsId(model.getId()));
         return response;
    }

}
