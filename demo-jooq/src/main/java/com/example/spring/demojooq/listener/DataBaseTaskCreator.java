package com.example.spring.demojooq.listener;

import com.example.spring.demojooq.model.Task;
import com.example.spring.demojooq.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataBaseTaskCreator {

    private final TaskService taskService;

//    @EventListener(ApplicationStartedEvent.class)
    public void createTaskData() {
        log.debug("Calling DataBaseTaskCreator createTaskData...");
        taskService.batchInsert(getGeneratedTaskList());
    }

    private List<Task> getGeneratedTaskList() {
        List<Task> taskList = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            Task task = new Task();
            task.setId((long) i);
            task.setTitle("Title" + i);
            task.setDescription("Description" + i);
            task.setPriority(i);
            taskList.add(task);
        }
        return taskList;
    }

}
