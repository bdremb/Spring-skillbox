package com.example.springstudents.listener;

import com.example.springstudents.event.AddStudentEvent;
import com.example.springstudents.event.DeleteStudentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StudentEventsListener {

    @EventListener({AddStudentEvent.class, DeleteStudentEvent.class})
    void handleStudentEvents(ApplicationEvent event) {
        log.info(event.toString());
    }
}
