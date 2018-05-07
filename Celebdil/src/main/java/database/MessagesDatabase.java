package main.java.database;

import main.java.data.Message;
import main.java.exception.InternalFailureException;

import java.util.List;
import java.util.UUID;

public interface MessagesDatabase {

    List<Message> readMessagesTo(UUID uuid) throws InternalFailureException;

    List<Message> readMessagesFrom(UUID uuid) throws InternalFailureException;

    List<Message> readMessagesBetween(UUID uuid1, UUID uuid2) throws InternalFailureException;

    Message readMessageById(UUID uuid) throws InternalFailureException;

    int writeMessage(Message message) throws InternalFailureException;
}
