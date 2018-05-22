package main.java.service;

import main.java.data.Message;
import main.java.database.MessagesDatabase;
import main.java.exception.InternalFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MessageService {

    @Autowired
    MessagesDatabase messagesDatabase;

    public int saveMessage(Message message) throws InternalFailureException {
        UUID uuid;
        do {
            uuid = UUID.randomUUID();
        } while(messagesDatabase.readMessageById(uuid) != null);
        message.setId(uuid);
        return messagesDatabase.writeMessage(message);
    }

    public Message getMessage(UUID messageId) throws InternalFailureException {
        return messagesDatabase.readMessageById(messageId);
    }

    public List<Message> getMessagesToUser(UUID accountNumber) throws InternalFailureException {
        return messagesDatabase.readMessagesTo(accountNumber);
    }

    public List<Message> getMessagesFromUser(UUID accountNumber) throws InternalFailureException {
        return messagesDatabase.readMessagesFrom(accountNumber);
    }

    public List<Message> getMessagesBetweenUsers(UUID accountNumber1, UUID accountNumber2) throws InternalFailureException {
        return messagesDatabase.readMessagesBetween(accountNumber1, accountNumber2);
    }
}
