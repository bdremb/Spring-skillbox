package com.example.springstudents.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
public class InitService {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${app.path}")
    private String filename;

    private final StorageService storageService;

    @EventListener(ApplicationStartedEvent.class)
    public void initStudents() {
        if (activeProfile.equals("init")) {
            try {
                Files.readAllLines(Paths.get(filename), Charset.defaultCharset())
                        .forEach(str -> {
                            String[] data = str.split(";");
                            storageService.addStudent(data[0], data[1], data[2]);
                        });
                log.info("Список студентов по умолчанию инициализирован!");
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

}
