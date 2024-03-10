package com.example.spring.spring.restapi.news.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewsFilter {

    private Integer pageSize;

    private Integer pageNumber;

    private String category;

    private String userName;

    private Long userId;

    private Long categoryId;

}
