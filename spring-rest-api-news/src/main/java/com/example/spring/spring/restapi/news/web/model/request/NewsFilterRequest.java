package com.example.spring.spring.restapi.news.web.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewsFilterRequest {

    private Integer pageSize;

    private Integer pageNumber;

    private String category;

    private String userName;

    private Long userId;

    private Long categoryId;

}
