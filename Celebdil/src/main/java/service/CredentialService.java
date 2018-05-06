package main.java.service;

import lombok.NonNull;
import main.java.data.LoginCredentials;
import main.java.database.LoginDatabase;
import main.java.exception.InternalFailureException;
import main.java.exception.InvalidLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * A service class for handling Credential logic
 */
@Service
public class CredentialService {

    @Autowired
    LoginDatabase loginDatabase;

    /**
     * Determines if the credentials supplied to us are valid or not
     * @param loginCredentials
     * @return
     */
    public LoginCredentials loginUser(@NonNull LoginCredentials loginCredentials) throws InternalFailureException, InvalidLoginException {
        LoginCredentials officialLoginCredentials = loginDatabase.readByUserName(loginCredentials.getUsername());
        if(!loginCredentials.getPassword().equals(officialLoginCredentials.getPassword())) {
            throw new InvalidLoginException("Incorrect username or password.");
        }
        return officialLoginCredentials;
    }
}
