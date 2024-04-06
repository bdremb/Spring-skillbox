package com.example.spring.spring.restapi.news.web.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewsFilterRequest {

    @Min(1)
    @Max(Integer.MAX_VALUE)
    @NotNull
    private Integer pageSize;

    @Min(0)
    @Max(Integer.MAX_VALUE)
    @NotNull
    private Integer pageNumber;

    @NotBlank
    private String category;

    @NotBlank
    private String userName;

    @Min(0)
    @Max(Integer.MAX_VALUE)
    private Long userId;

    @Min(0)
    @Max(Integer.MAX_VALUE)
    private Long categoryId;

}
