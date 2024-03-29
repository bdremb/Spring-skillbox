package com.example.spring.spring.restapi.news.web.model.response;

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
    private UserResponse user;
    private NewsItemResponse newsItem;
    private Instant createdAt;
    private Instant updatedAt;
}
