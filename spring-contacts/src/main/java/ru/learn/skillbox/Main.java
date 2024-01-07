package ru.learn.skillbox;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.learn.skillbox.config.AppConfig;
import ru.learn.skillbox.service.PersonService;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        PersonService personService = context.getBean(PersonService.class);
        personService.start();
    }
}
