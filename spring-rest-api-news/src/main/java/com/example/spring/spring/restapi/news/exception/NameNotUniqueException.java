package com.example.spring.spring.restapi.news.exception;

public class NameNotUniqueException extends RuntimeException {
    public NameNotUniqueException(String message) {
        super(message);
    }
}
