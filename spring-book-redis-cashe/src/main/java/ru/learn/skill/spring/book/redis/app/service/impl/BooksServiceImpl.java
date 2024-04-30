package ru.learn.skill.spring.book.redis.app.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
import static java.util.Objects.nonNull;
import static ru.learn.skill.spring.book.redis.app.config.properties.AppCacheProperties.CacheNames.BOOKS_BY_CATEGORY;
import static ru.learn.skill.spring.book.redis.app.config.properties.AppCacheProperties.CacheNames.BOOKS_BY_NAME_AND_AUTHOR;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheManager = "redisCacheManager")
public class BooksServiceImpl implements BooksService {

    private final BookRepository repository;
    private final CategoryRepository categoryRepository;
    private final BookMapper mapper;

    @Override
    @Cacheable(value = BOOKS_BY_CATEGORY, key="#category")
    public List<BookResponse> findAll(String category) {
        return mapper.toResponseList(repository.findAll());
    }

    @Override
    @Cacheable(value = BOOKS_BY_NAME_AND_AUTHOR, key = "#name + #author")
    public BookResponse findByNameAndAuthor(String name, String author) {
        return mapper.toResponse(getBookByIdOrFail(name, author));
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = BOOKS_BY_CATEGORY, allEntries = true),
            @CacheEvict(value = BOOKS_BY_NAME_AND_AUTHOR, allEntries = true)
    })
    public BookResponse create(BookRequest request) {
        CategoryEntity category = CategoryEntity.builder().name(request.getCategoryName()).build();
        BookEntity createdBook = mapper.toModel(request);
        createdBook.setCategory( categoryRepository.save(category));
        return mapper.toResponse(repository.save(createdBook));
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = BOOKS_BY_CATEGORY, allEntries = true),
            @CacheEvict(value = BOOKS_BY_NAME_AND_AUTHOR, key = "#name + #author", beforeInvocation = true)
    })
    public BookResponse update(String name, String author, BookRequest request) {
        BookEntity existsBook = getBookByIdOrFail(name, author);
        if (nonNull(request.getCategoryName())) {
            CategoryEntity existsCategory = categoryRepository.findById(existsBook.getCategory().getId())
                    .orElseThrow(() -> new EntityNotFoundException(format("Category with id={0} not found", existsBook.getCategory().getId())));
            existsCategory.setName(request.getCategoryName());
            existsBook.setCategory(categoryRepository.save(existsCategory));
        }
        BookEntity updatedBook = mapper.toUpdatedModel(existsBook, request);
        return mapper.toResponse(repository.save(updatedBook));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = BOOKS_BY_CATEGORY, allEntries = true),
            @CacheEvict(value = BOOKS_BY_NAME_AND_AUTHOR, key = "#name + #author", beforeInvocation = true)
    })
    public void deleteById(String name, String author) {
        BookEntity bookEntity = getBookByIdOrFail(name, author);
        repository.delete(bookEntity);
    }

    private BookEntity getBookByIdOrFail(String name, String author) {
        return repository.getBookEntityByNameAndAuthor(name, author)
                .orElseThrow(() -> new EntityNotFoundException(format("Book with name={0} author={1} not found", name, author)));
    }

}
