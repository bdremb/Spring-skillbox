package com.example.spring.spring.restapi.news.web.model.response;

import com.example.spring.spring.restapi.news.model.Comment;
import com.example.spring.spring.restapi.news.model.NewsCategory;
import com.example.spring.spring.restapi.news.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsItemResponse {
    private Long id;
    private String text;
    private User user;
    private NewsCategory category;
    private List<Comment> comments = new ArrayList<>();
    private Long commentsCount;
    private Instant createdAt;
    private Instant updatedAt;
}
