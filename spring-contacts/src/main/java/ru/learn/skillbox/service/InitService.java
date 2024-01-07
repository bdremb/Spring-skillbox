package ru.learn.skillbox.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.learn.skillbox.model.ContactStorage;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.System.out;

@Service
public class InitService {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    private final ContactStorage contactStorage;
    private final String filename;

    public InitService(ContactStorage contactStorage, String filename) {
        this.contactStorage = contactStorage;
        this.filename = filename;
    }

    @PostConstruct
    private void init() {
        if (activeProfile.equals("init")) {
            try {
                Files.readAllLines(Paths.get(filename), Charset.defaultCharset()).forEach(contactStorage::addContact);
                out.println("Контакты инициализированы!");
            } catch (IOException e) {
                out.println(e.getMessage());
            }
        }
    }

}
