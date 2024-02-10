package com.example.spring.springdatacontacts.repository;


import com.example.spring.springdatacontacts.model.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactRepository {

    List<Contact> findAll();

    Optional<Contact> getById(Long id);

    Contact save(Contact contact);

    Contact update(Contact contact);

    void deleteById(Long id);

    void batchInsert(List<Contact> contacts);

}
