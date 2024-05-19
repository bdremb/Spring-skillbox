package ru.learn.skill.spring.flux.task.tracker.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.learn.skill.spring.flux.task.tracker.entity.User;

import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {

    private String id;
    private String name;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private String status;
    private User author;
    private User assignee;
    private Set<User> observers;
}
