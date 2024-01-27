package com.example.springstudents.mapper;


import com.example.springstudents.model.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public Student toStudent(String firstName, String lastName, String age, int id) {
        return Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .age(Integer.parseInt(age))
                .studentId(id)
                .build();
    }
}
