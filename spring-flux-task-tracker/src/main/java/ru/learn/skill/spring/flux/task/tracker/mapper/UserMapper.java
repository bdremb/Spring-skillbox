package ru.learn.skill.spring.flux.task.tracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.learn.skill.spring.flux.task.tracker.entity.User;
import ru.learn.skill.spring.flux.task.tracker.web.model.request.UserRequest;
import ru.learn.skill.spring.flux.task.tracker.web.model.response.UserResponse;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    UserResponse toResponse(User model);

    default Flux<UserResponse> toFluxResponse(Flux<User> models) {
        return models.map(this::toResponse);
    }

    default Mono<UserResponse> toMonoResponse(Mono<User> model) {
        return model.map(this::toResponse);
    }

    User toUser(UserRequest request);

    @Mapping(target = "email", source = "request.email", defaultValue = "user.email")
    @Mapping(target = "username", source = "request.username", defaultValue = "user.username")
    User toUpdatedUser(User user, UserRequest request);

}

