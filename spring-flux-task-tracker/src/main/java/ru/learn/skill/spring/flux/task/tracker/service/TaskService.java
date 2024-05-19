package ru.learn.skill.spring.flux.task.tracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.learn.skill.spring.flux.task.tracker.entity.Task;
import ru.learn.skill.spring.flux.task.tracker.entity.User;
import ru.learn.skill.spring.flux.task.tracker.mapper.TaskMapper;
import ru.learn.skill.spring.flux.task.tracker.reposotory.TaskRepository;
import ru.learn.skill.spring.flux.task.tracker.reposotory.UserRepository;
import ru.learn.skill.spring.flux.task.tracker.web.model.request.TaskRequest;
import ru.learn.skill.spring.flux.task.tracker.web.model.response.TaskResponse;

import java.time.Instant;
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


    public Mono<List<TaskResponse>> findAll() {
        Mono<List<Task>> allMonoTasks = taskRepository.findAll().collectList();
        Mono<List<User>> allMonoUsers = userRepository.findAll().collectList();
        return Mono.zip(allMonoTasks, allMonoUsers)
                .flatMap(data -> {
                    final List<Task> tasks = data.getT1();
                    final Map<String, User> users = data.getT2().stream()
                            .collect(toMap(User::getId, identity()));
                    tasks.forEach(task -> {
                        task.setAuthor(users.get(task.getAuthorId()));
                        task.setAssignee(users.get(task.getAssigneeId()));
                        final Set<String> observerIds = task.getObserverIds();

                        task.setObservers(users.keySet().stream()
                                .filter(observerIds::contains)
                                .map(users::get).collect(toSet()));
                    });
                    return Mono.just(taskMapper.toListResponse(tasks));
                });
    }


    public Mono<TaskResponse> create(TaskRequest request) {
        Task createdTask = taskMapper.toTask(request, Instant.now());
//        return taskMapper.toMonoResponse(taskRepository.save(createdTask).map(this::fillTask));
        return null;
    }

}
