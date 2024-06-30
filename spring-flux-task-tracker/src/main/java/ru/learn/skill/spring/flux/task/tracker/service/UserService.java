package ru.learn.skill.spring.flux.task.tracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.learn.skill.spring.flux.task.tracker.entity.RoleType;
import ru.learn.skill.spring.flux.task.tracker.entity.User;
import ru.learn.skill.spring.flux.task.tracker.exception.NameNotUniqueException;
import ru.learn.skill.spring.flux.task.tracker.mapper.UserMapper;
import ru.learn.skill.spring.flux.task.tracker.reposotory.UserRepository;
import ru.learn.skill.spring.flux.task.tracker.web.model.request.UserRequest;
import ru.learn.skill.spring.flux.task.tracker.web.model.response.UserResponse;

import java.util.Set;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public Mono<UserResponse> createNewAccount(UserRequest request, RoleType roleType) {
        if (isUserNameAlreadyExists(request.getUsername())) {
            throw new NameNotUniqueException("User name already exists");
        }
        User user = userMapper.toUser(request);
        user.setRoles(Set.of(roleType));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toMonoResponse(userRepository.save(user));
    }

    public Flux<UserResponse> findAll() {
        return userMapper.toFluxResponse(userRepository.findAll());
    }

    public Mono<UserResponse> findById(String id) {
        return userMapper.toMonoResponse(userRepository.findById(id));
    }

//    public Mono<UserResponse> create(UserRequest request) {
//        User createdUser = userMapper.toUser(request);
//        return userMapper.toMonoResponse(userRepository.save(createdUser));
//    }

    public Mono<UserResponse> update(String id, UserRequest request) {
        return userMapper.toMonoResponse(userRepository.findById(id)
                .flatMap(userForUpdate ->
                        userRepository.save(userMapper.toUpdatedUser(userForUpdate, request))));
    }

    public Mono<Void> deleteById(String id) {
        return userRepository.deleteById(id);
    }

    public Mono<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    private boolean isUserNameAlreadyExists(String username) {
        return nonNull(findUserByUsername(username).share().block());
    }

}


