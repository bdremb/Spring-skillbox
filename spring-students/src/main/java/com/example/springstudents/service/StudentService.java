package com.example.springstudents.service;

import com.example.springstudents.event.AddStudentEvent;
import com.example.springstudents.event.DeleteStudentEvent;
import com.example.springstudents.event.InitStudentsEvent;
import com.example.springstudents.mapper.StudentMapper;
import com.example.springstudents.model.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.nonNull;

@Slf4j
@ShellComponent
@RequiredArgsConstructor
public class StudentService implements ApplicationEventPublisherAware {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    private final Map<Integer, Student> studentMap = new HashMap<>();
    private final StudentMapper mapper;
    protected ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void init() {
        if (activeProfile.equals("init")) {
            applicationEventPublisher.publishEvent(new InitStudentsEvent(activeProfile));
        }
    }

    @ShellMethod(key = "add", value = "Add new student. Example: add --f first name --l last name --age how old is the student")
    public void addStudent(
            @ShellOption("f") String firstName,
            @ShellOption("l") String lastName,
            String age
    ) {
        Student student = mapper.toStudent(firstName, lastName, age, getId());
        studentMap.put(student.getStudentId(), student);
        applicationEventPublisher.publishEvent(new AddStudentEvent(student));
    }

    @ShellMethod(key = "delete", value = "Delete student by id. Example: delete --id (student id)")
    public void deleteContact(Integer id) {
        studentMap.remove(id);
        applicationEventPublisher.publishEvent(new DeleteStudentEvent(id));
    }

    @ShellMethod(key = "delete all", value = "Deleted all students")
    public String deleteAllContact() {
        studentMap.clear();
        return "Deleted all students";
    }

    @ShellMethod(key = "get", value = "Get student by id. Example: get --id (student id)")
    public String getContact(Integer id) {
        Student student = studentMap.get(id);
        return nonNull(student) ? student.toString() : MessageFormat.format("Student with id: {0} not found", id);
    }

    @ShellMethod(key = "all", value = "Get all students")
    public String getContacts() {
        StringBuilder sb = new StringBuilder();
        studentMap.values().forEach(student -> sb.append(student.toString()).append("\n"));
        return sb.isEmpty() ? "The list of students is empty..." : sb.toString();
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
