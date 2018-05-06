package main.java.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalFailureException extends Exception {

    public InternalFailureException(String message) {
        super(message);
    }

}
