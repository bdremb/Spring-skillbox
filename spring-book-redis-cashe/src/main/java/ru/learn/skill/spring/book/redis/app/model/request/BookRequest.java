package ru.learn.skill.spring.book.redis.app.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {

    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    private String author;
    private String info;
    @NotEmpty
    private String categoryName;
}
