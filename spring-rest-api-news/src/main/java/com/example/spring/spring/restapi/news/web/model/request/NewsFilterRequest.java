package com.example.spring.spring.restapi.news.web.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsFilterRequest {

    private Integer pageSize;

    private Integer pageNumber;

    private String category;

    private String userName;

    private Long userId;

    private Long categoryId;

}
