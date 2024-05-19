package ru.learn.skill.spring.flux.task.tracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.learn.skill.spring.flux.task.tracker.entity.User;
import ru.learn.skill.spring.flux.task.tracker.exception.EntityNotFoundException;
import ru.learn.skill.spring.flux.task.tracker.mapper.UserMapper;
import ru.learn.skill.spring.flux.task.tracker.reposotory.UserRepository;
import ru.learn.skill.spring.flux.task.tracker.web.model.request.UserRequest;
import ru.learn.skill.spring.flux.task.tracker.web.model.response.UserResponse;

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Flux<UserResponse> findAll() {
        return userMapper.toFluxResponse(userRepository.findAll());
    }

    public Mono<UserResponse> findById(String id) {
        return userMapper.toMonoResponse(userRepository.findById(id));
    }

    public Mono<UserResponse> create(UserRequest request) {
        User createdUser = userMapper.toUser(request);
        return userMapper.toMonoResponse(userRepository.save(createdUser));
    }

    public Mono<UserResponse> update(String id, UserRequest request) {
        final User existedUser = userRepository.findById(id).blockOptional()
                .orElseThrow(() -> new EntityNotFoundException(format("User with id={0} not found", id)));
        User updatedUser = userMapper.toUpdatedUser(existedUser, request);
        return userMapper.toMonoResponse(userRepository.save(updatedUser));
    }

    public Mono<Void> deleteById(String id) {
        return userRepository.deleteById(id);
    }

}

