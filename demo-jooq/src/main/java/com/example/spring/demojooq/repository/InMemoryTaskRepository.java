package com.example.spring.demojooq.repository;

import com.example.spring.demojooq.model.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Slf4j
@Repository
public class InMemoryTaskRepository implements TaskRepository {

    private final List<Task> tasks = new ArrayList<>();

    @Override
    public List<Task> findAll() {
        log.debug("Call findAll");
        return tasks;
    }

    @Override
    public Optional<Task> getById(Long id) {
        log.debug("Call getById {}", id);
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }

    @Override
    public Task save(Task task) {
        log.debug("Call save {}", task);
        task.setId(System.currentTimeMillis());
        tasks.add(task);
        return task;
    }

    @Override
    public Task update(Task task) {
        log.debug("Call update task {}", task);
        Task existingTask = getById(task.getId()).orElse(null);
        if (nonNull(existingTask)) {
            existingTask.setDescription(task.getDescription());
            existingTask.setPriority(task.getPriority());
            existingTask.setTitle(task.getTitle());
        }
        return existingTask;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Call delete by id {}", id);
        getById(id).ifPresent(tasks::remove);
    }

    @Override
    public void batchInsert(List<Task> tasks) {
        this.tasks.addAll(tasks);
    }

}
