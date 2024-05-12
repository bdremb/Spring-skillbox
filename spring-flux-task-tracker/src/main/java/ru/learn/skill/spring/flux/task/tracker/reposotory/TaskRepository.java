package ru.learn.skill.spring.flux.task.tracker.reposotory;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.learn.skill.spring.flux.task.tracker.entity.Task;

public interface TaskRepository extends ReactiveMongoRepository<Task, String> {
}
