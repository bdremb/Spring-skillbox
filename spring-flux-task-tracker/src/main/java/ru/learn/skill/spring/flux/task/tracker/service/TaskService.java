package ru.learn.skill.spring.flux.task.tracker.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public Flux<TaskResponse> findAll() {
        return taskMapper.toFluxResponse(
                Mono.zip(
                        taskRepository.findAll().collectList(),
                        userRepository.findAll().collectList()
                ).flatMap(data -> {
                    final List<Task> tasks = data.getT1();
                    return Mono.just(tasks.stream()
                            .map(task -> taskMapper.toTask(task, data.getT2()))
                            .toList());
                }).flatMapMany(Flux::fromIterable)
        ).onErrorResume(e -> Mono.just(new TaskResponse()));
    }

    public Mono<TaskResponse> create(TaskRequest request) {
        Task createdTask = taskMapper.toTask(request, Instant.now());
        List<String> specificUserIds = new ArrayList<>(request.getObserverIds());
        specificUserIds.addAll(List.of(request.getAssigneeId(), request.getAuthorId()));

        return taskMapper.toMonoResponse(
                Mono.zip(
                        taskRepository.save(createdTask),
                        userRepository.findAllById(specificUserIds).collectList()
                ).map(data -> taskMapper.toTask(data.getT1(), data.getT2()))
        ).onErrorResume(e -> Mono.just(new TaskResponse()));
    }

    public Mono<TaskResponse> findById(String id) {
        return taskMapper.toMonoResponse(
                Mono.zip(
                        taskRepository.findById(id),
                        userRepository.findAll().collectList()
                ).map(data -> taskMapper.toTask(data.getT1(), data.getT2()))
        ).onErrorResume(e -> Mono.just(new TaskResponse()));
    }

    public Mono<TaskResponse> update(String id, TaskRequest request) {
        return taskMapper.toMonoResponse(
                taskRepository.findById(id)
                        .flatMap(taskForUpdate -> {
                            final Mono<Task> savedTask = taskRepository.save(taskMapper.toUpdatedTask(taskForUpdate, request));
                            final Mono<List<User>> allMonoUsers = userRepository.findAll().collectList();
                            return Mono.zip(savedTask, allMonoUsers)
                                    .map(data -> taskMapper.toTask(data.getT1(), data.getT2()));
                        })
        ).onErrorResume(e -> Mono.just(new TaskResponse()));
    }

    public Mono<TaskResponse> addObserver(String id, String observerId) {
        return taskMapper.toMonoResponse(
                taskRepository.findById(id)
                        .flatMap(updatedTask -> {
                            updatedTask.getObserverIds().add(observerId);
                            return Mono.zip(
                                    taskRepository.save(updatedTask),
                                    userRepository.findAll().collectList(),
                                    userRepository.findById(observerId)
                            ).map(data -> {
                                data.getT3();
                                return taskMapper.toTask(data.getT1(), data.getT2());
                            });
                        })
        ).onErrorResume(e -> Mono.just(new TaskResponse()));
    }

    public Mono<Void> deleteById(String id) {
        return taskRepository.deleteById(id);
    }

}
