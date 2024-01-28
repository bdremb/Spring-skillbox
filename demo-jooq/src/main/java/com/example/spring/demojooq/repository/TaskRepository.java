package com.example.spring.demojooq.repository;


import com.example.spring.demojooq.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    List<Task> findAll();

    Optional<Task> getById(Long id);

    Task save(Task task);

    Task update(Task task);

    void deleteById(Long id);

    void batchInsert(List<Task> tasks);

}
