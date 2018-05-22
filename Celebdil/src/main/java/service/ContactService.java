package main.java.service;

import main.java.data.Contact;
import main.java.data.Contact.ContactStatus;
import main.java.database.ContactsDatabase;
import main.java.exception.InternalFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ContactService {

    @Autowired
    ContactsDatabase contactsDatabase;

    public int addContact(UUID userId1, UUID userId2, ContactStatus contactStatus) throws InternalFailureException {
        int writes = 0;
        Contact contact = contactsDatabase.readContactPair(userId1, userId2);
        if(contact == null) {
            contact = new Contact();
            contact.setContactStatus(contactStatus);
            if(userId1.toString().compareTo(userId2.toString()) > 0) {
                UUID temp = userId2;
                userId2 = userId1;
                userId1 = temp;
                contact.flipStatus();
            }
            contact.setUser1(userId1);
            contact.setUser2(userId2);
            writes = contactsDatabase.writeContact(contact);
        }
        return writes;
    }
}
