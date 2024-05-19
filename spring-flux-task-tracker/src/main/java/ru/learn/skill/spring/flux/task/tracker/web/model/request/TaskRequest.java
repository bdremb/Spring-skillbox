package ru.learn.skill.spring.flux.task.tracker.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {
    private String name;
    private String description;
    private String status;
    private String authorId;
    private String assigneeId;
    private Set<String> observerIds;
}
