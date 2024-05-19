package ru.learn.skill.spring.flux.task.tracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.learn.skill.spring.flux.task.tracker.entity.Task;
import ru.learn.skill.spring.flux.task.tracker.entity.User;
import ru.learn.skill.spring.flux.task.tracker.mapper.TaskMapper;
import ru.learn.skill.spring.flux.task.tracker.reposotory.TaskRepository;
import ru.learn.skill.spring.flux.task.tracker.reposotory.UserRepository;
import ru.learn.skill.spring.flux.task.tracker.web.model.request.TaskRequest;
import ru.learn.skill.spring.flux.task.tracker.web.model.response.TaskResponse;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;


    public Flux<TaskResponse> findAll() {
        Mono<List<Task>> allMonoTasks = taskRepository.findAll().collectList();
        Mono<List<User>> allMonoUsers = userRepository.findAll().collectList();

        return taskMapper.toFluxResponse(
                Mono.zip(allMonoTasks, allMonoUsers)
                        .flatMap(data -> {
                            final List<Task> tasks = data.getT1();
                            return Mono.just(tasks.stream()
                                    .map(task -> taskMapper.toTask(task, data.getT2()))
                                    .toList());
                        }).flatMapMany(Flux::fromIterable)
        );
    }

    public Mono<TaskResponse> create(TaskRequest request) {
        Task createdTask = taskMapper.toTask(request, Instant.now());
        final Mono<Task> savedTask = taskRepository.save(createdTask);
        List<String> ids = new ArrayList<>(request.getObserverIds());
        ids.addAll(List.of(request.getAssigneeId(), request.getAuthorId()));
        Mono<List<User>> specificMonoUsers = userRepository.findAllById(ids).collectList();

        return taskMapper.toMonoResponse(
                Mono.zip(savedTask, specificMonoUsers)
                        .flatMap(data ->
                                Mono.just(taskMapper.toTask(data.getT1(), data.getT2())))
        );
    }

    public Mono<TaskResponse> findById(String id) {
        Mono<Task> task = taskRepository.findById(id);
        Mono<List<User>> allMonoUsers = userRepository.findAll().collectList();
        return taskMapper.toMonoResponse(
                Mono.zip(task, allMonoUsers)
                        .flatMap(data ->
                                Mono.just(taskMapper.toTask(data.getT1(), data.getT2())))
        );
    }

    public Mono<TaskResponse> update(String id, TaskRequest request) {
        Mono<Task> task = taskRepository.findById(id);
        Mono<Task> updatedTask = taskMapper.toUpdatedMonoTask(task, request);
        Mono<List<User>> allMonoUsers = userRepository.findAll().collectList();
        return taskMapper.toMonoResponse(
                Mono.zip(updatedTask, allMonoUsers)
                        .flatMap(data -> {
                                    final Mono<Task> savedTask = taskRepository.save(data.getT1());
                                    return Mono.zip(savedTask, allMonoUsers)
                                            .flatMap(data1 ->
                                                    Mono.just(taskMapper.toTask(data1.getT1(), data1.getT2())));
                                }
                        )
        );
    }


}
