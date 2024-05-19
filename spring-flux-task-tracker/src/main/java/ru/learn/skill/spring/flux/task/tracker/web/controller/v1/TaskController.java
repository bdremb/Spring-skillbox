package ru.learn.skill.spring.flux.task.tracker.web.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.learn.skill.spring.flux.task.tracker.service.TaskService;
import ru.learn.skill.spring.flux.task.tracker.web.model.request.TaskRequest;
import ru.learn.skill.spring.flux.task.tracker.web.model.response.TaskResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public Mono<List<TaskResponse>> getAll() {
        return taskService.findAll();
    }

//    @GetMapping("/{id}")
//    public Mono<ResponseEntity<TaskResponse>> getById(@PathVariable String id) {
//        return taskService.findById(id)
//                .map(ResponseEntity::ok)
//                .defaultIfEmpty(ResponseEntity.notFound().build());
//    }
//
    @PostMapping
    public Mono<ResponseEntity<TaskResponse>> create(@RequestBody TaskRequest request) {
        return taskService.create(request)
                .map(ResponseEntity::ok);
    }
//
//    @PutMapping("/{id}")
//    public Mono<ResponseEntity<TaskResponse>> update(@PathVariable String id, @RequestBody TaskRequest request) {
//        return taskService.update(id, request)
//                .map(ResponseEntity::ok)
//                .defaultIfEmpty(ResponseEntity.notFound().build());
//    }
//
//    @DeleteMapping("/{id}")
//    public Mono<ResponseEntity<Void>> deleteItem(@PathVariable String id) {
//        return taskService.deleteById(id)
//                .then(Mono.just(ResponseEntity.noContent().build()));
//    }

}
