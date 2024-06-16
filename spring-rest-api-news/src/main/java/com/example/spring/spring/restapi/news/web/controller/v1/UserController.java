package com.example.spring.spring.restapi.news.web.controller.v1;

import com.example.spring.spring.restapi.news.aop.EntityType;
import com.example.spring.spring.restapi.news.aop.OwnerVerification;
import com.example.spring.spring.restapi.news.service.UserService;
import com.example.spring.spring.restapi.news.web.model.request.UserRequest;
import com.example.spring.spring.restapi.news.web.model.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    @OwnerVerification(entityType = EntityType.USER)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER','ROLE_MODERATOR')")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping("/{id}")
    @OwnerVerification(entityType = EntityType.USER)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER','ROLE_MODERATOR')")
    public ResponseEntity<UserResponse> update(
            @PathVariable("id") Long userId,
            @RequestBody @Valid UserRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(userService.update(userId, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER','ROLE_MODERATOR')")
    @OwnerVerification(entityType = EntityType.USER)
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
