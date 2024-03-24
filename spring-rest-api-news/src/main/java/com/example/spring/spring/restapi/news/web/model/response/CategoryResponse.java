package com.example.spring.spring.restapi.news.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {

    private Long id;
    private String categoryName;
    private Instant createdAt;
    private Instant updatedAt;
}
