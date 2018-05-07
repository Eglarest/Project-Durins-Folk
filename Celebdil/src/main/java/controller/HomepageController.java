package main.java.controller;

import main.java.data.Message;
import main.java.exception.InternalFailureException;
import main.java.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class HomepageController {

    @Autowired
    private MessageService messageService;

    /**
     * This API will return a greeting to the user
     * @return
     */
    @RequestMapping(method = GET, value = "/home")
    public @ResponseBody Message greeting() throws InternalFailureException {
        Message initialGreeting = messageService.getMessage(UUID.fromString("0-0-0-0-0"));
        return initialGreeting;
    }
}