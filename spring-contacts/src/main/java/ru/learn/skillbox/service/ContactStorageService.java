package ru.learn.skillbox.service;

import org.springframework.stereotype.Service;
import ru.learn.skillbox.model.Person;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;
import static java.text.MessageFormat.format;

@Service
public class ContactStorageService {

    private final Map<String, Person> contactsMap;
    public static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String PHONE_PATTERN = "^\\+?[1-9][0-9]{7,14}$";

    public ContactStorageService() {
        this.contactsMap = new HashMap<>();
    }

    public boolean addContact(String contact) {
        Person person = getPerson(contact);
        if (!person.getEmail().matches(EMAIL_PATTERN)) {
            out.println(format("Некорректный адрес email: {0}", person.getEmail()));
            return false;
        }
        if (!person.getPhoneNumber().matches(PHONE_PATTERN)) {
            out.println(format("Некорректный номер телефона {0}", person.getPhoneNumber()));
            return false;
        }
        contactsMap.put(person.getEmail(), person);
        return true;
    }

    public void deleteContact(String email) {
        contactsMap.remove(email);
    }

    public Person getContact(String email) {
        return contactsMap.get(email);
    }

    public String getContacts() {
        StringBuilder sb = new StringBuilder();
        contactsMap.values().forEach(c -> sb.append(c.getName()).append(';').append(c.getPhoneNumber()).append(';').append(c.getEmail()).append("\n"));
        return sb.toString();
    }

    public Map<String, Person> getContactsMap() {
        return contactsMap;
    }

    private Person getPerson(String contactString) {
        String[] data = contactString.split(";");
        return new Person(data[0], data[1], data[2]);
    }

}
