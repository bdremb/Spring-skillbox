package ru.learn.skill.spring.flux.task.tracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.learn.skill.spring.flux.task.tracker.entity.Task;
import ru.learn.skill.spring.flux.task.tracker.web.model.request.TaskRequest;
import ru.learn.skill.spring.flux.task.tracker.web.model.response.TaskResponse;

import java.time.Instant;

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

    Task toUpdatedTask(String id, TaskRequest request, Instant updatedAt);

}

