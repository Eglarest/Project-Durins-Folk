package main.java.service;

import main.java.data.Address;
import main.java.data.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A service class for handling User logic
 */
@Service
public class UserService {

    /**
     * Get the User by their account number
     * @param accountNumber
     * @return
     */
    public User getUserByAccountNumber(UUID accountNumber) {
        User user = new User();
        user.setTitle("Sir");
        user.setFirstName("Robin");
        user.setMiddleName("The");
        user.setSurName("Fearless");
        user.setSuffix("II");
        user.setAccountNumber(accountNumber);
        user.setAddress(new Address());
        return user;
    }

    /**
     * Get a list of Users who have similar or the same name as provided
     * @param string
     * @return
     */
    public List<User> getUsersByString(String string) {
        // TODO: Get Users who have similar or matching names
        User user = getUserByAccountNumber(UUID.randomUUID());
        user.setFirstName(string);
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        users.add(user);
        users.add(user);
        return users;
    }

    /**
     * Create a new User. We will not yet know their AccountNumber.
     * @param user
     * @return
     */
    public User createNewUser(User user) {
        user.setAccountNumber(UUID.randomUUID());
        return user;
    }

}
