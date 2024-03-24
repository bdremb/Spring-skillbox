package com.example.spring.spring.restapi.news.web.model.response;

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
    private UserResponse user;
    private NewsCategoryResponse category;
    private List<CommentResponse> comments = new ArrayList<>();
    private Instant createdAt;
    private Instant updatedAt;
}
