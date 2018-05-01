package main.java.service;

import com.google.common.base.Strings;
import main.java.exception.InvalidParameterException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ValidationService {

    public void validateUUID(String uuid, String description) throws InvalidParameterException {
        String genericException = "UUID of invalid format. Proper format: " +
                "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx (8-4-4-4-12) where x is in [0-9,a-f]";
        if(uuid == null) {
            throw new InvalidParameterException(description + "; parameter cannot be null.");
        }
        try {
            UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException(String.format("%s; %s", description, genericException));
        }
    }

    public void isNotNullOrEmpty(String string, String description) throws InvalidParameterException {
        String genericException = "String cannot be null or empty";
        if(Strings.isNullOrEmpty(string)) {
            throw new InvalidParameterException(String.format("%s; %s", description, genericException));
        }
    }
}
