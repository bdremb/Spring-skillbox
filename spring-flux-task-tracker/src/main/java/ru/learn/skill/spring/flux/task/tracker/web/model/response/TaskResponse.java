package ru.learn.skill.spring.flux.task.tracker.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {

    private String id;
    private String name;
    private Integer description;
    private Instant createdAt;
    private Instant updatedAt;
    private String status;
    private String authorId;
    private String assigneeId;
    private Set<String> observerIds;
}
