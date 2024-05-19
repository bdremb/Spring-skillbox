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
import java.util.Map;
import java.util.Set;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;


    public Flux<TaskResponse> findAll() {
        Mono<List<Task>> allMonoTasks = taskRepository.findAll().collectList();
        Mono<List<User>> allMonoUsers = userRepository.findAll().collectList();
        return taskMapper.toFluxResponse(Mono.zip(allMonoTasks, allMonoUsers)
                .flatMap(data -> {
                    final List<Task> tasks = data.getT1();
                    final Map<String, User> users = data.getT2().stream()
                            .collect(toMap(User::getId, identity()));
                    tasks.forEach(task -> setUsers(task, users));
                    return Mono.just(tasks);
                }).flatMapMany(Flux::fromIterable));
    }


    public Mono<TaskResponse> create(TaskRequest request) {
        Task createdTask = taskMapper.toTask(request, Instant.now());
        final Mono<Task> savedTask = taskRepository.save(createdTask);
        List<String> ids = new ArrayList<>(request.getObserverIds());
        ids.addAll(List.of(request.getAssigneeId(), request.getAuthorId()));
        Mono<List<User>> specificMonoUsers = userRepository.findAllById(ids).collectList();
        return taskMapper.toMonoResponse(Mono.zip(savedTask, specificMonoUsers)
                .flatMap(data -> {
                    final Task task = data.getT1();
                    final Map<String, User> users = data.getT2().stream()
                            .collect(toMap(User::getId, identity()));
                    setUsers(task, users);
                    return Mono.just(task);
                }));
    }

    private static void setUsers(Task task, Map<String, User> users) {
        task.setAuthor(users.get(task.getAuthorId()));
        task.setAssignee(users.get(task.getAssigneeId()));
        final Set<String> observerIds = task.getObserverIds();
        task.setObservers(users.keySet().stream()
                .filter(observerIds::contains)
                .map(users::get).collect(toSet()));
    }


}
