package ru.learn.skill.spring.flux.task.tracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
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

    Task toUpdatedTask(String id, TaskRequest request, Instant updatedAt);

}

