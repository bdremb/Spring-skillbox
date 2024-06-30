package ru.learn.skill.spring.flux.task.tracker.web.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.learn.skill.spring.flux.task.tracker.service.TaskService;
import ru.learn.skill.spring.flux.task.tracker.web.model.request.TaskRequest;
import ru.learn.skill.spring.flux.task.tracker.web.model.response.TaskResponse;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_USER')")
    public Flux<TaskResponse> getAll() {
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_USER')")
    public Mono<ResponseEntity<TaskResponse>> getById(@PathVariable String id) {
        return taskService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
    public Mono<ResponseEntity<TaskResponse>> create(@RequestBody TaskRequest request) {
        return taskService.create(request)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
    public Mono<ResponseEntity<TaskResponse>> update(@PathVariable String id, @RequestBody TaskRequest request) {
        return taskService.update(id, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/add-observer/{observerId}")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
    public Mono<ResponseEntity<TaskResponse>> addObserver(@PathVariable String id, @PathVariable String observerId) {
        return taskService.addObserver(id, observerId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER')")
    public Mono<ResponseEntity<Void>> deleteItem(@PathVariable String id) {
        return taskService.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

}
