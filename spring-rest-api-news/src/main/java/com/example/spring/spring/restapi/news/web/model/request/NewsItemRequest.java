package com.example.spring.spring.restapi.news.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsItemRequest {
    private String text;
    private String categoryName;
}
