package com.example.springstudents.event;

import com.example.springstudents.model.Student;
import org.springframework.context.ApplicationEvent;

public class AddStudentEvent extends ApplicationEvent {

    public AddStudentEvent(Student student) {
        super(student);
    }

    @Override
    public String toString() {
        return "Add student :: " + this.getSource();
    }

}
