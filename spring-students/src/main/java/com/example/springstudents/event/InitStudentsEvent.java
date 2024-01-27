package com.example.springstudents.event;

import org.springframework.context.ApplicationEvent;

public class InitStudentsEvent extends ApplicationEvent {

    public InitStudentsEvent(String profile) {
        super(profile);
    }

    @Override
    public String toString() {
        return "Список студентов по умолчанию инициализирован! Profile :: " + this.getSource();
    }

}
