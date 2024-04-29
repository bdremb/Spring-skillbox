package ru.learn.skill.spring.book.redis.app.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.learn.skill.spring.book.redis.app.model.request.BookRequest;
import ru.learn.skill.spring.book.redis.app.model.response.BookResponse;
import ru.learn.skill.spring.book.redis.app.service.BooksService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BooksController {

    private final BooksService service;

    @GetMapping
    public ResponseEntity<List<BookResponse>> findAll(@RequestParam(required = false) String category) {
        return ResponseEntity.ok(service.findAll(category));
    }

    @GetMapping("/name/{name}/category/{category}")
    public ResponseEntity<BookResponse> findByNameCategoryKey(@PathVariable String name, @PathVariable String category) {
        return ResponseEntity.ok(service.findByNameCategoryKey(name, category));
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(@RequestBody @Valid BookRequest request) {
        return ResponseEntity.status(CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> update(
            @PathVariable("id") Long bookId,
            @RequestBody @Valid BookRequest request
    ) {
        return ResponseEntity.ok(service.update(bookId, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long bookId) {
        service.deleteById(bookId);
        return ResponseEntity.noContent().build();
    }

}
