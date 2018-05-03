package main.java.controller;

import main.java.data.Message;
import main.java.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class HomepageController {

    @Autowired
    private MessageService messageService;

    /**
     * This API will take in an optional user and return a personalized homepage greeting to that user
     * @param name
     * @return
     */
    @RequestMapping(method = GET, value = "/home")
    public @ResponseBody Message greeting(@RequestParam(value="name", defaultValue="User") String name) {
        Message initialGreeting = messageService.getMessagesFromUser(UUID.fromString("0-0-0-0-0")).get(0);
        initialGreeting.setId(UUID.randomUUID());
        initialGreeting.setContent("Welcome to Project-Durins-Folk, " + name + "! Sit back and have an ale!");
        initialGreeting.setTo(UUID.randomUUID());
        initialGreeting.setDate(new Date());
        return initialGreeting;
    }
}