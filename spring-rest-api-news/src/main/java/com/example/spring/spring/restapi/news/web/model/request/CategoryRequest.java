package com.example.spring.spring.restapi.news.web.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    @NotBlank
    @NotNull
    @Length(max = 255, min = 2)
    private String categoryName;
}
