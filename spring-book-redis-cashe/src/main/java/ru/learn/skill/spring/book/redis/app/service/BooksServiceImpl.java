package ru.learn.skill.spring.book.redis.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.learn.skill.spring.book.redis.app.model.request.BookRequest;
import ru.learn.skill.spring.book.redis.app.model.response.BookResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {

    @Override
    public List<BookResponse> findAll(String category) {
        return List.of();
    }

    @Override
    public BookResponse findByNameCategoryKey(String name, String author) {
        return null;
    }

    @Override
    public BookResponse create(BookRequest request) {
        return null;
    }

    @Override
    public BookResponse update(Long id, BookRequest request) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
