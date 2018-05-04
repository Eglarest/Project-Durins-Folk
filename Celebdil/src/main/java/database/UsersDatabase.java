package main.java.database;

import main.java.data.User;

import java.util.List;
import java.util.UUID;

public interface UsersDatabase {

    User readUserById(UUID uuid);

    List<User> readUserByString(String string);

    boolean writeUser(User user);
}
