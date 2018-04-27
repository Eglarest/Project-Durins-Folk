package main.java.service;

import com.google.common.base.Strings;
import main.java.data.LoginCredentials;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {

    /**
     * This service determines if the credentials supplied to us are valid or not
     * @param loginCredentials
     * @return
     */
    public boolean isValidLogin(LoginCredentials loginCredentials) {
        if(loginCredentials == null || Strings.isNullOrEmpty(loginCredentials.getUsername()) ||
                Strings.isNullOrEmpty(loginCredentials.getPassword())) {
            return false;
        }
        return ("Durin".equals(loginCredentials.getUsername()) && "Thorin".equals(loginCredentials.getPassword()));
    }
}
