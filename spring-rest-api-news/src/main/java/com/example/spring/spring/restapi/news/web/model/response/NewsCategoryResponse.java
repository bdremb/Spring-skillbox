package com.example.spring.spring.restapi.news.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsCategoryResponse {

    private Long id;

    private String categoryName;
}
