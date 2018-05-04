package main.java.database;

import main.java.data.Contact;

import java.util.UUID;

public interface ContactsDatabase {

    Contact readContactByUser(UUID uuid);

    boolean writeContact(Contact contact);
}
