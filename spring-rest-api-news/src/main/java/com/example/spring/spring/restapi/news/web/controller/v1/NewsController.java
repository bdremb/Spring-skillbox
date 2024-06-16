package com.example.spring.spring.restapi.news.web.controller.v1;

import com.example.spring.spring.restapi.news.aop.EntityType;
import com.example.spring.spring.restapi.news.aop.OwnerVerification;
import com.example.spring.spring.restapi.news.service.NewsService;
import com.example.spring.spring.restapi.news.web.model.request.NewsFilterRequest;
import com.example.spring.spring.restapi.news.web.model.request.NewsItemRequest;
import com.example.spring.spring.restapi.news.web.model.response.NewsItemResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/news-items")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<List<NewsItemResponse>> findAll(
            @RequestParam @Min(1) @Max(Integer.MAX_VALUE) @NotNull Integer pageSize,
            @RequestParam @Min(0) @Max(Integer.MAX_VALUE) @NotNull Integer pageNumber,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) @Min(0) @Max(Integer.MAX_VALUE) Long userId,
            @RequestParam(required = false) @Min(0) @Max(Integer.MAX_VALUE) Long categoryId
    ) {
        return ResponseEntity.ok(newsService.findAll(
                NewsFilterRequest.builder()
                        .pageSize(pageSize)
                        .pageNumber(pageNumber)
                        .category(category)
                        .userName(userName)
                        .userId(userId)
                        .categoryId(categoryId)
                        .build()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsItemResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.findById(id));
    }

    @PostMapping("/user/{userName}")
    public ResponseEntity<NewsItemResponse> create(@PathVariable String userName, @RequestBody @Valid NewsItemRequest request) {
        return ResponseEntity.status(CREATED).body(newsService.create(userName, request));
    }

    @PutMapping("/{id}")
    @OwnerVerification(entityType = EntityType.NEWS)
    public ResponseEntity<NewsItemResponse> update(
            @PathVariable("id") Long newsItemId,
            @RequestBody @Valid NewsItemRequest request
    ) {
        return ResponseEntity.ok(newsService.update(newsItemId, request));
    }

    @DeleteMapping("/{id}")
    @OwnerVerification(entityType = EntityType.NEWS)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        newsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
