package main.java.service;

import main.java.data.User;
import main.java.database.UsersDatabase;
import main.java.exception.InternalFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * A service class for handling User logic
 */
@Service
public class UserService {

    @Autowired
    UsersDatabase usersDatabase;

    public static final int UUID_ATTEMPTS = 3;

    /**
     * Get the User by their account number
     * @param accountNumber
     * @return
     */
    public User getUserByAccountNumber(UUID accountNumber) throws InternalFailureException {
        return usersDatabase.readUserById(accountNumber);
    }

    /**
     * Get a list of Users who have similar or the same name as provided
     * @param string
     * @return
     */
    public List<User> findUsersByString(String string) throws InternalFailureException {
        return usersDatabase.readUsersByString(string);
    }

    /**
     * Create a new User. We will not yet know their AccountNumber.
     * @param user
     * @return
     */
    public User createNewUser(User user) throws InternalFailureException {
        UUID uuid;
        for(int i = 0; i < UUID_ATTEMPTS; i++) {
            uuid = UUID.randomUUID();
            if(usersDatabase.isKeyAvailable(uuid)) {
                user.setAccountNumber(uuid);
                usersDatabase.writeUser(user);
                return user;
            }
        }
        throw new InternalFailureException("Unable to find available Users Database Key, " +
                "database may be getting full or retries may need to be increased.");
    }

}
