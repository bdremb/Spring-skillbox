package ru.learn.skill.spring.flux.task.tracker.reposotory;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.learn.skill.spring.flux.task.tracker.entity.User;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findUserByUsername(String username);
}
