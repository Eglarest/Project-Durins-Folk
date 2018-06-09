package main.java.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DateTimeFormatException extends Exception{

    public DateTimeFormatException(String message) {
        super(message);
    }

    public DateTimeFormatException(String message, Exception e) {
        super(message, e);
    }
}
