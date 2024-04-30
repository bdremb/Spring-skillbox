package ru.learn.skill.spring.book.redis.app.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse implements Serializable {
    private Long id;
    private String name;
    private String author;
    private String info;
    private String categoryName;
}
