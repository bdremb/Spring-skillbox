package ru.learn.skill.spring.flux.task.tracker.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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
import java.util.Set;

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
                                getUsersByTaskId(id, null)
                        )
                        .map(data -> taskMapper.toTask(data.getT1(), data.getT2()))
        ).onErrorResume(e -> Mono.just(new TaskResponse()));
    }

    public Mono<TaskResponse> update(String id, TaskRequest request) {
        return taskMapper.toMonoResponse(
                taskRepository.findById(id)
                        .flatMap(taskForUpdate -> {
                            List<String> newUserIds = new ArrayList<>(request.getObserverIds());
                            newUserIds.addAll(List.of(request.getAssigneeId(), request.getAuthorId()));
                            final Mono<List<User>> allMonoUsers = getUsersByTaskId(id, newUserIds);
                            final Mono<Task> savedTask = taskRepository.save(taskMapper.toUpdatedTask(taskForUpdate, request));
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
                                    getUsersByTaskId(id, null),
                                    userRepository.findById(observerId)
                            ).map(data -> {
                                data.getT3();
                                return taskMapper.toTask(data.getT1(), data.getT2());
                            });
                        })
        ).onErrorResume(e -> Mono.just(new TaskResponse()));
    }

    private Mono<List<User>> getUsersByTaskId(String taskId, List<String> optionalIds) {
        return taskRepository.findById(taskId)
                .flatMap(task -> userRepository.findAllById(getUserIdsByTask(task, optionalIds)).collectList());
    }

    private Set<String> getUserIdsByTask(Task task, List<String> optionalIds) {
        final Set<String> userIds = task.getObserverIds();
        userIds.add(task.getAssigneeId());
        userIds.add(task.getAuthorId());
        if (!CollectionUtils.isEmpty(optionalIds) && !optionalIds.contains(null)) {
            userIds.addAll(optionalIds);
        }
        return userIds;
    }

    public Mono<Void> deleteById(String id) {
        return taskRepository.deleteById(id);
    }

}
