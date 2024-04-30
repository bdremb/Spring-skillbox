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

    @GetMapping("/name/{name}/author/{author}")
    public ResponseEntity<BookResponse> findByNameCategoryKey(@PathVariable String name, @PathVariable String author) {
        return ResponseEntity.ok(service.findByNameAndAuthor(name, author));
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(@RequestBody @Valid BookRequest request) {
        return ResponseEntity.status(CREATED).body(service.create(request));
    }

    @PutMapping("/name/{name}/author/{author}")
    public ResponseEntity<BookResponse> update(
            @PathVariable String name, @PathVariable String author,
            @RequestBody @Valid BookRequest request
    ) {
        return ResponseEntity.ok(service.update(name, author, request));
    }

    @DeleteMapping("/name/{name}/author/{author}")
    public ResponseEntity<Void> delete(@PathVariable String name, @PathVariable String author) {
        service.deleteById(name, author);
        return ResponseEntity.noContent().build();
    }

}
