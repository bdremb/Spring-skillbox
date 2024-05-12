package ru.learn.skill.spring.flux.task.tracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import reactor.core.publisher.Flux;
import ru.learn.skill.spring.flux.task.tracker.entity.Task;
import ru.learn.skill.spring.flux.task.tracker.web.model.response.TaskResponse;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TaskMapper {

    TaskResponse toResponse(Task model);

    default Flux<TaskResponse> toFluxResponse(Flux<Task> models) {
        return models.map(this::toResponse);
    }

}
