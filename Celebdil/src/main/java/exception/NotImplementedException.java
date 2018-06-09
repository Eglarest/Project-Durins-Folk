package main.java.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
public class NotImplementedException extends Exception {

    public NotImplementedException() {
        super("This endpoint is not yet implemented: Please check back at a future time!");
    }

    public NotImplementedException(String message) {
        super(message);
    }

    public NotImplementedException(String message, Exception e) {
        super(message, e);
    }
}
