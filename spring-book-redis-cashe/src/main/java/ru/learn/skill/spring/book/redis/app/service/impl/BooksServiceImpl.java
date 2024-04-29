package ru.learn.skill.spring.book.redis.app.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.learn.skill.spring.book.redis.app.entity.BookEntity;
import ru.learn.skill.spring.book.redis.app.entity.CategoryEntity;
import ru.learn.skill.spring.book.redis.app.mapper.BookMapper;
import ru.learn.skill.spring.book.redis.app.model.request.BookRequest;
import ru.learn.skill.spring.book.redis.app.model.response.BookResponse;
import ru.learn.skill.spring.book.redis.app.repository.BookRepository;
import ru.learn.skill.spring.book.redis.app.repository.CategoryRepository;
import ru.learn.skill.spring.book.redis.app.service.BooksService;

import java.util.List;

import static java.text.MessageFormat.format;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {

    private final BookRepository repository;
    private final CategoryRepository categoryRepository;
    private final BookMapper mapper;


    @Override
    public List<BookResponse> findAll(String category) {
        return mapper.toResponseList(repository.findAll());
    }

    @Override
    public BookResponse findByNameCategoryKey(String name, String author) {
        return mapper.toResponse(repository.getBookEntityByNameAndAuthor(name, author));
    }

    @Override
    public BookResponse create(BookRequest request) {
        CategoryEntity category = categoryRepository.findByName(request.getCategoryName()).orElse(null);
        if (isNull(category)) {
            category = CategoryEntity.builder().name(request.getCategoryName()).build();
        }
        categoryRepository.save(category);
        BookEntity createdBook = mapper.toModel(request, category);
        return mapper.toResponse(repository.save(createdBook));
    }

    @Override
    public BookResponse update(Long id, BookRequest request) {
        BookEntity existsBook = getBookByIdOrFail(id);
        BookEntity updatedBook = mapper.toUpdatedModel(existsBook, request);
        return mapper.toResponse(repository.save(updatedBook));
    }

    @Override
    public void deleteById(Long id) {
        repository.delete(getBookByIdOrFail(id));
    }

    private BookEntity getBookByIdOrFail(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("Book with id={0} not found", id)));
    }

}
