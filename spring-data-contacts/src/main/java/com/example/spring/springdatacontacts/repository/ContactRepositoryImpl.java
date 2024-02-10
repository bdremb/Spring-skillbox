package com.example.spring.springdatacontacts.repository;


import com.example.spring.springdatacontacts.exception.EntityNotFoundException;
import com.example.spring.springdatacontacts.jooq.db.tables.records.ContactRecord;
import com.example.spring.springdatacontacts.model.Contact;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.spring.springdatacontacts.jooq.db.Tables.CONTACT;

@Slf4j
@Repository
@Primary
@RequiredArgsConstructor
public class ContactRepositoryImpl implements ContactRepository {

    private final DSLContext dslContext;

    @Override
    public List<Contact> findAll() {
        return dslContext.selectFrom(CONTACT)
                .fetchInto(Contact.class);
    }

    @Override
    public Optional<Contact> getById(Long id) {
        return dslContext.selectFrom(CONTACT)
                .where(CONTACT.ID.eq(id))
                .fetchOptional()
                .map(contactRecord -> contactRecord.into(Contact.class));
    }

    @Override
    public Contact save(Contact contact) {
        contact.setId(System.currentTimeMillis());

        ContactRecord contactRecord = dslContext.newRecord(CONTACT, contact);
        contactRecord.store();

        return contactRecord.into(Contact.class);
    }

    @Override
    @SneakyThrows
    public Contact update(Contact contact) {
        final Optional<ContactRecord> updatedContactRecord = dslContext.update(CONTACT)
                .set(dslContext.newRecord(CONTACT, contact))
                .where(CONTACT.ID.eq(contact.getId()))
                .returning()
                .fetchOptional();

        return updatedContactRecord.map(contactRecord -> contactRecord.into(com.example.spring.springdatacontacts.model.Contact.class))
                .orElseThrow(() -> new EntityNotFoundException("Contact not found"));
    }

    @Override
    public void deleteById(Long id) {
        dslContext.deleteFrom(CONTACT)
                .where(CONTACT.ID.eq(id))
                .execute();
    }

}
