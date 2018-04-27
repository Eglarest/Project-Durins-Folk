package main.java.service;

import com.google.common.base.Strings;
import main.java.data.LoginCredentials;
import org.springframework.stereotype.Service;

/**
 * A service class for handling credential logic
 */
@Service
public class CredentialService {

    /**
     * Determines if the credentials supplied to us are valid or not
     * @param loginCredentials
     * @return
     */
    public boolean isValidLogin(LoginCredentials loginCredentials) {
        // Basic validity check
        if(loginCredentials == null || Strings.isNullOrEmpty(loginCredentials.getUsername()) ||
                Strings.isNullOrEmpty(loginCredentials.getPassword())) {
            return false;
        }
        // Call to see if this pairing is valid
        return ("Durin".equals(loginCredentials.getUsername()) && "Thorin".equals(loginCredentials.getPassword()));
    }
}
