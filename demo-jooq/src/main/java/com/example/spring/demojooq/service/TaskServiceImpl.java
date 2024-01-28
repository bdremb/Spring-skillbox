package com.example.spring.demojooq.service;

import com.example.spring.demojooq.model.Task;
import com.example.spring.demojooq.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    @Override
    public List<Task> findAll() {
        log.debug("TaskServiceImpl.findAll");
        return repository.findAll();
    }

    @Override
    public Task getById(Long id) {
        log.debug("TaskServiceImpl.getById");
        return repository.getById(id).orElse(null);
    }

    @Override
    public Task save(Task task) {
        log.debug("TaskServiceImpl.save");
        return repository.save(task);
    }

    @Override
    public Task update(Task task) {
        log.debug("TaskServiceImpl.update");
        return repository.update(task);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("TaskServiceImpl.delete");
        repository.deleteById(id);
    }

    @Override
    public void batchInsert(List<Task> tasks) {
        log.debug("TaskServiceImpl.batchInsert");
        repository.batchInsert(tasks);
    }

}
