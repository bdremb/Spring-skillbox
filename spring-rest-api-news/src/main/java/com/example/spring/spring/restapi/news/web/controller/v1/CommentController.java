package com.example.spring.spring.restapi.news.web.controller.v1;

import com.example.spring.spring.restapi.news.aop.Action;
import com.example.spring.spring.restapi.news.aop.EntityType;
import com.example.spring.spring.restapi.news.aop.OwnerVerification;
import com.example.spring.spring.restapi.news.service.CommentService;
import com.example.spring.spring.restapi.news.web.model.request.CommentRequest;
import com.example.spring.spring.restapi.news.web.model.response.CommentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER','ROLE_MODERATOR')")
    public ResponseEntity<List<CommentResponse>> findAllByNewsItemId(@RequestParam Long newsItemId) {
        return ResponseEntity.ok(commentService.findAllByNewsItemId(newsItemId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER','ROLE_MODERATOR')")
    public ResponseEntity<CommentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER','ROLE_MODERATOR')")
    public ResponseEntity<CommentResponse> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid CommentRequest request
    ) {
        return ResponseEntity.status(CREATED).body(commentService.create(userDetails.getUsername(), request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER','ROLE_MODERATOR')")
    @OwnerVerification(entityType = EntityType.COMMENT, action = Action.UPDATE)
    public ResponseEntity<CommentResponse> update(
            @PathVariable("id") Long commentId,
            @RequestBody @Valid CommentRequest request
    ) {
        return ResponseEntity.ok(commentService.update(commentId, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER','ROLE_MODERATOR')")
    @OwnerVerification(entityType = EntityType.COMMENT, action = Action.DELETE)
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
