package ru.learn.skill.spring.flux.task.tracker.exception;

public class NameNotUniqueException extends RuntimeException {
    public NameNotUniqueException(String message) {
        super(message);
    }
}
