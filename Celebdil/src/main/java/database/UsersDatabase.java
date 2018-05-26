package main.java.database;

import main.java.data.User;
import main.java.exception.InternalFailureException;

import java.util.List;
import java.util.UUID;

public interface UsersDatabase {

    User readUserById(UUID accountId) throws InternalFailureException;

    List<User> readUsersByString(String string) throws InternalFailureException;

    boolean isKeyAvailable(UUID accountId) throws InternalFailureException;

    int writeUser(User user) throws InternalFailureException;

}
