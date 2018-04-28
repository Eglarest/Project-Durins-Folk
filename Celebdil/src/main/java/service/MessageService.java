package main.java.service;

import main.java.data.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MessageService {

    public boolean saveMessage(Message message) {
        message.setId(UUID.randomUUID());
        return true;
    }

    public Message getMessage(UUID messageId) {
        Message message = new Message();
        message.setId(messageId);
        return message;
    }

    public List<Message> getMessagesToUser(UUID accountNumber) {
        Message message = new Message();
        message.setTo(accountNumber);
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message);
        messages.add(message);
        messages.add(message);
        return messages;
    }

    public List<Message> getMessagesFromUser(UUID accountNumber) {
        Message message = new Message();
        message.setFrom(accountNumber);
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message);
        messages.add(message);
        messages.add(message);
        return messages;
    }

    public List<Message> getMessagesBetweenUsers(UUID accountNumber1, UUID accountNumber2) {
        Message message1 = new Message();
        message1.setFrom(accountNumber1);
        message1.setTo(accountNumber2);
        Message message2 = new Message();
        message2.setTo(accountNumber1);
        message2.setFrom(accountNumber2);
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);
        messages.add(message1);
        return messages;
    }
}
