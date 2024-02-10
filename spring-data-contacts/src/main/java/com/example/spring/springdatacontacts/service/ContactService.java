package com.example.spring.springdatacontacts.service;


import com.example.spring.springdatacontacts.model.Contact;

import java.util.List;

public interface ContactService {

    List<Contact> findAll();

    Contact getById(Long id);

    Contact save(Contact contact);

    Contact update(Contact contact);

    void deleteById(Long id);

    void batchInsert(List<Contact> contacts);
}
