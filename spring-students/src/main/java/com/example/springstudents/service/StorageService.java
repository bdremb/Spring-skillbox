package com.example.springstudents.service;

import com.example.springstudents.mapper.StudentMapper;
import com.example.springstudents.model.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;

@Slf4j
@ShellComponent
@RequiredArgsConstructor
public class StorageService {

    private final Map<Integer, Student> studentMap;
    private final StudentMapper mapper;

    @ShellMethod(key = "add")
    public String addStudent(
            @ShellOption(value = "fn") String firstName,
            @ShellOption(value = "ln") String lastName,
            String age
    ) {
        Student student = mapper.toStudent(firstName, lastName, age, getId());
        studentMap.put(student.getStudentId(), student);
        return MessageFormat.format("Added new student: {0}", student);
    }

    @ShellMethod(key = "delete")
    public String deleteContact(Integer id) {
        studentMap.remove(id);
        return MessageFormat.format("Deleted student with id: {0}", id);
    }

    @ShellMethod(key = "get")
    public Student getContact(Integer id) {
        return studentMap.get(id);
    }

    @ShellMethod(key = "all")
    public String getContacts() {
        StringBuilder sb = new StringBuilder();
        studentMap.values().forEach(student -> sb.append(student.toString()).append("\n"));
        return sb.toString();
    }

    private int getId() {
        Set<Integer> ids = studentMap.keySet();
        Integer id = 0;
        while (ids.contains(id)) {
            id++;
        }
        return id;
    }

}
