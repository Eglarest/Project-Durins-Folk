package main.java.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidLoginException extends Exception{

    public InvalidLoginException(String message) {
        super(message);
    }

    public InvalidLoginException(String message, Exception e) {
        super(message, e);
    }

}
