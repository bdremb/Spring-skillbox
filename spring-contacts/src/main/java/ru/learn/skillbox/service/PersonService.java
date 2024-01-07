package ru.learn.skillbox.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Scanner;

import static java.lang.System.out;
import static java.text.MessageFormat.format;
import static java.util.Objects.nonNull;

@Service
public class PersonService {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${app.helpMessage}")
    private String helpMessage;

    private final ContactStorageService contactStorage;
    private final String filename;

    public PersonService(ContactStorageService contacts, String filename) {
        this.contactStorage = contacts;
        this.filename = filename;
    }

    public void start() {
        out.println("Активный профиль - " + activeProfile);
        boolean isAppRunning = true;
        while (isAppRunning) {
            out.println("\n\nВведите команду. <help> - помощь.");
            Scanner scanner = new Scanner(System.in);
            String command = scanner.next();
            switch (command) {
                case "help" -> out.println(helpMessage);
                case "all" -> printAllContacts();
                case "get" -> getContact();
                case "add" -> addContact();
                case "delete" -> deleteContact();
                case "exit" -> isAppRunning = false;
                default -> out.println("Команда <" + command + "> не корректная");
            }
        }
        out.println("Работа приложения завершена!");
    }

    private void printAllContacts() {
        out.println("\nТекущие контакты:");
        contactStorage.getContactsMap().values().forEach(out::println);
    }

    private void addContact() {
        out.println("Добавьте новый контакт в формате «Ivanov Ivan Petrovich.;номер телефона;адрес электронной почты.");
        Scanner scanner = new Scanner(System.in);
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), Charset.defaultCharset()))) {
            String newContact = scanner.nextLine();
            if (contactStorage.addContact(newContact)) {
                writer.write(contactStorage.getContacts());
                out.println(format("Новый контакт {0} добавлен", newContact));
            }
        } catch (Exception e) {
            out.println(e.getMessage());
        }
    }

    private void getContact() {
        out.println("Введите адрес электронной почты контакта.");
        Scanner scanner = new Scanner(System.in);
        String email = scanner.next();
        if (isPersonExists(email)) {
            out.println(contactStorage.getContact(email));
        } else {
            out.println(format("Контакт с email={0} не найден", email));
        }
    }

    private void deleteContact() {
        out.println("Введите адрес электронной почты удаляемого контакта.");
        Scanner scanner = new Scanner(System.in);
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), Charset.defaultCharset()))) {
            String email = scanner.next();
            if (isPersonExists(email)) {
                contactStorage.deleteContact(email);
                writer.write(contactStorage.getContacts());
            } else {
                out.println(format("Контакт с email={0} не найден", email));
            }
        } catch (Exception e) {
            out.println(e.getMessage());
        }
        out.println("Контакт удален");
    }

    private boolean isPersonExists(String email) {
        return nonNull(contactStorage.getContact(email));
    }

}
