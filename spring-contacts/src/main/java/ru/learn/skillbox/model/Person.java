package ru.learn.skillbox.model;


import java.util.Objects;

public class Person {

    private final String name;
    private final String phoneNumber;
    private final String email;


    public Person(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) && Objects.equals(phoneNumber, person.phoneNumber) && Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber, email);
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return  "<<"+name + " | " + phoneNumber  + " | " + email + ">>";
    }
}
