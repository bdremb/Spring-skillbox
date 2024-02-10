package com.example.spring.springdatacontacts.service;

import com.example.spring.springdatacontacts.model.Contact;
import com.example.spring.springdatacontacts.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository repository;

    @Override
    public List<Contact> findAll() {
        return repository.findAll();
    }

    @Override
    public Contact getById(Long id) {
        return repository.getById(id).orElse(null);
    }

    @Override
    public Contact save(Contact contact) {
        return repository.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        return repository.update(contact);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
