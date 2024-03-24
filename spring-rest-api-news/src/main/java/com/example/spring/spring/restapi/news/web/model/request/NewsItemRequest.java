package com.example.spring.spring.restapi.news.web.model.request;

import com.example.spring.spring.restapi.news.web.model.response.CommentResponse;
import com.example.spring.spring.restapi.news.web.model.response.NewsCategoryResponse;
import com.example.spring.spring.restapi.news.web.model.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsItemRequest {
    private String text;
    private String categoryName;
    private UserResponse user;
    private NewsCategoryResponse category;
    private List<CommentResponse> comments;
    private Instant createdAt;
    private Instant updatedAt;
}
