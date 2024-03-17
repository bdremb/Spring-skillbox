package com.example.spring.spring.restapi.news.web.model.response;

import com.example.spring.spring.restapi.news.model.NewsItem;
import com.example.spring.spring.restapi.news.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private String commentText;
    private User user;
    private NewsItem newsItem;
    private Instant createdAt;
    private Instant updatedAt;
}
