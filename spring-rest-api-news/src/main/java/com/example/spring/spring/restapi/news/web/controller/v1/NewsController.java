package com.example.spring.spring.restapi.news.web.controller.v1;

import com.example.spring.spring.restapi.news.aop.EntityType;
import com.example.spring.spring.restapi.news.aop.OwnerVerification;
import com.example.spring.spring.restapi.news.model.NewsFilter;
import com.example.spring.spring.restapi.news.service.NewsService;
import com.example.spring.spring.restapi.news.web.model.request.NewsItemRequest;
import com.example.spring.spring.restapi.news.web.model.response.NewsItemResponse;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/news-items")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<List<NewsItemResponse>> findAll(NewsFilter newsFilter) {
        return ResponseEntity.ok(newsService.findAll(newsFilter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsItemResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.findById(id));
    }

    @PostMapping("/user/{userName}")
    public ResponseEntity<NewsItemResponse> create(@PathVariable String userName, @RequestBody @Valid NewsItemRequest request) {
        return ResponseEntity.status(CREATED).body(newsService.create(userName, request));
    }

    @PutMapping("/{id}/user/{userName}")
    @OwnerVerification(entityType = EntityType.NEWS)
    public ResponseEntity<NewsItemResponse> update(
            @PathVariable("id") Long newsItemId,
            @PathVariable String userName,
            @RequestBody @Valid NewsItemRequest request
    ) {
        return ResponseEntity.ok(newsService.update(newsItemId, userName, request));
    }

    @DeleteMapping("/{id}/user/{userName}")
    @OwnerVerification(entityType = EntityType.NEWS)
    public ResponseEntity<Void> delete(@PathVariable Long id, @PathVariable String userName) {
        newsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
