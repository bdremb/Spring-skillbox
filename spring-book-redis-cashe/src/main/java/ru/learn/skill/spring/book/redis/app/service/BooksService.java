package ru.learn.skill.spring.book.redis.app.service;

import ru.learn.skill.spring.book.redis.app.model.request.BookRequest;
import ru.learn.skill.spring.book.redis.app.model.response.BookResponse;

import java.util.List;

public interface BooksService {
    List<BookResponse> findAll(String category);

    BookResponse findByNameCategoryKey(String name, String author);

    BookResponse create(BookRequest request);

    BookResponse update(Long id, BookRequest request);

    void deleteById(Long id);
}
