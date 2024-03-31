package com.example.spring.spring.restapi.news.web.controller.v1;

import com.example.spring.spring.restapi.news.aop.EntityType;
import com.example.spring.spring.restapi.news.aop.OwnerVerification;
import com.example.spring.spring.restapi.news.service.CommentService;
import com.example.spring.spring.restapi.news.web.model.request.CommentRequest;
import com.example.spring.spring.restapi.news.web.model.response.CommentResponse;
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
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentResponse>> findAllByNewsItemId(Long newsItemId) {
        return ResponseEntity.ok(commentService.findAllByNewsItemId(newsItemId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.findById(id));
    }

    @PostMapping("/user/{userName}")
    public ResponseEntity<CommentResponse> create(@PathVariable String userName, @RequestBody @Valid CommentRequest request) {
        return ResponseEntity.status(CREATED).body(commentService.create(userName, request));
    }

    @PutMapping("/{id}/user/{userName}")
    @OwnerVerification(entityType = EntityType.COMMENT)
    public ResponseEntity<CommentResponse> update(
            @PathVariable("id") Long commentId,
            @PathVariable String userName,
            @RequestBody @Valid CommentRequest request
    ) {
        return ResponseEntity.ok(commentService.update(commentId, request));
    }

    @DeleteMapping("/{id}/user/{userName}")
    @OwnerVerification(entityType = EntityType.COMMENT)
    public ResponseEntity<Void> delete(@PathVariable Long id, @PathVariable String userName) {
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
