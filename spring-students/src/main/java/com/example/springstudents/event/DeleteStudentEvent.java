package com.example.springstudents.event;

import org.springframework.context.ApplicationEvent;

public class DeleteStudentEvent extends ApplicationEvent {

    public DeleteStudentEvent(Integer id) {
        super(id);
    }

    @Override
    public String toString() {
        return "Delete student :: " + this.getSource();
    }

}
