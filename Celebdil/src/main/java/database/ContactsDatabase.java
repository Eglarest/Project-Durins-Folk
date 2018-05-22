package main.java.database;

import main.java.data.Contact;
import main.java.exception.InternalFailureException;

import java.util.List;
import java.util.UUID;

public interface ContactsDatabase {

    List<Contact> readContactsByUserId(UUID userId) throws InternalFailureException;

    public Contact readContactPair(UUID lowerUserId, UUID higherUserId) throws InternalFailureException;

    int writeContact(Contact contact) throws InternalFailureException;

}
