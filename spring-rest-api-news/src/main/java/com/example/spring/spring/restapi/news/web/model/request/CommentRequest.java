package com.example.spring.spring.restapi.news.web.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {

    @Min(0)
    @NotNull
    private Long newsItemId;

    @NotBlank
    @NotNull
    @Length(max = 255, min = 2)
    private String commentText;
}
