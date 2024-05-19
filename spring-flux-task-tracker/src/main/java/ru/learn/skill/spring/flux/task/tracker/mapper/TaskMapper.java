package ru.learn.skill.spring.flux.task.tracker.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.learn.skill.spring.flux.task.tracker.entity.Task;
import ru.learn.skill.spring.flux.task.tracker.entity.User;
import ru.learn.skill.spring.flux.task.tracker.web.model.request.TaskRequest;
import ru.learn.skill.spring.flux.task.tracker.web.model.response.TaskResponse;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.nonNull;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TaskMapper {

    TaskResponse toResponse(Task model);

    default Mono<TaskResponse> toMonoResponse(Mono<Task> model) {
        return model.map(this::toResponse);
    }

    default Flux<TaskResponse> toFluxResponse(Flux<Task> models) {
        return models.map(this::toResponse);
    }

    Task toTask(TaskRequest request, Instant createdAt);

    default Task toTask(Task task, List<User> users) {
        final Map<String, User> usersMap = users.stream()
                .collect(toMap(User::getId, identity()));
        final Set<String> observerIds = task.getObserverIds();
        task.setAuthor(usersMap.get(task.getAuthorId()));
        task.setAssignee(usersMap.get(task.getAssigneeId()));
        task.setObservers(usersMap.keySet().stream()
                .filter(observerIds::contains)
                .map(usersMap::get).collect(toSet()));
        return task;
    }

    @Mapping(target = "name", source = "request.name", defaultValue = "task.name")
    @Mapping(target = "description", source = "request.description", defaultValue = "task.description")
    @Mapping(target = "authorId", source = "request.authorId", defaultValue = "task.authorId")
    @Mapping(target = "assigneeId", source = "request.assigneeId", defaultValue = "task.assigneeId")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "observerIds", ignore = true)
    Task toUpdatedTask(Task task, TaskRequest request);

    default Mono<Task> toUpdatedMonoTask(Mono<Task> task, TaskRequest request) {
        return task.map(t -> toUpdatedTask(t, request));
    }

    @AfterMapping
    default void afterToUpdatedTask(@MappingTarget Task task, TaskRequest request) {
        if (nonNull(request.getStatus())) {
            task.setStatus(Task.TaskStatus.valueOf(request.getStatus()));
        }
        if(!CollectionUtils.isEmpty(request.getObserverIds())) {
            task.setObserverIds(request.getObserverIds());
        }
        task.setUpdatedAt(Instant.now());
    }

}

