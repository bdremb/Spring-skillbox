package com.example.springstudents.model;

import lombok.Builder;
import lombok.Data;

import java.text.MessageFormat;

@Builder
@Data
public class Student {

    private final int studentId;
    private final String firstName;
    private final String lastName;
    private final int age;

    @Override
    public String toString() {
        return MessageFormat.format("id={2}: Student: First name-{0}, Last name-{1}, age={3}", firstName, lastName, studentId, age);
    }
}
