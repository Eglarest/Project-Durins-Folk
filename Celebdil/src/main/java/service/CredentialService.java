package main.java.service;

import lombok.NonNull;
import main.java.data.LoginCredentials;
import main.java.database.LoginDatabase;
import main.java.exception.InternalFailureException;
import main.java.exception.InvalidLoginException;
import main.java.exception.NotImplementedException;
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
     * @param loginCredentials credentials to attempt to log a user in
     * @return official credentials
     */
    public LoginCredentials loginUser(@NonNull LoginCredentials loginCredentials) throws InternalFailureException, InvalidLoginException {
        LoginCredentials officialLoginCredentials = loginDatabase.readLoginByUsername(loginCredentials.getUsername());
        if(!loginCredentials.getPassword().equals(officialLoginCredentials.getPassword())) {
            throw new InvalidLoginException("Incorrect username or password.");
        }
        return officialLoginCredentials;
    }

    public LoginCredentials createLogin(@NonNull LoginCredentials loginCredentials) throws NotImplementedException {
        //TODO: Create a new Login
        throw new NotImplementedException("CredentialService.createLogin(...) not yet implemented");
    }

    public LoginCredentials changePassword(@NonNull LoginCredentials loginCredentials, String newPassword) throws NotImplementedException {
        //TODO: Change a password for a user given:
        // * username
        // * accountId
        // * password
        // * String new password
        throw new NotImplementedException("CredentialService.changePassword(...) not yet implemented");
    }
}
