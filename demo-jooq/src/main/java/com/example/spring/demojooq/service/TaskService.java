package com.example.spring.demojooq.service;


import com.example.spring.demojooq.model.Task;

import java.util.List;

public interface TaskService {

    List<Task> findAll();

    Task getById(Long id);

    Task save(Task task);

    Task update(Task task);

    void deleteById(Long id);

    void batchInsert(List<Task> tasks);
}
