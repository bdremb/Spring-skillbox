package com.example.springstudents.service;

import com.example.springstudents.event.InitStudentsEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class InitService {

    @Value("${app.path}")
    private String filename;

    private final StudentService storageService;

    @EventListener(InitStudentsEvent.class)
    private void initStudents() {
        try {
            Files.readAllLines(Paths.get(filename), Charset.defaultCharset())
                    .forEach(str -> {
                        String[] data = str.split(";");
                        storageService.addStudent(data[0], data[1], data[2]);
                    });
        } catch (IOException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }

}
