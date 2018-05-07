package main.java.database;

import main.java.data.Message;
import main.java.exception.InternalFailureException;

import java.util.List;
import java.util.UUID;

public interface MessagesDatabase {

    List<Message> readMessagesTo(UUID uuid);

    List<Message> readMessagesFrom(UUID uuid);

    Message readMessageById(UUID uuid) throws InternalFailureException;

    boolean writeMessage(Message message);
}
