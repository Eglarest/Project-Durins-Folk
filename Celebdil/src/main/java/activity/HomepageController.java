package main.java.activity;

import main.java.data.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class HomepageController {

    /**
     * This API will take in an optional user and return a personalized homepage greeting to that user
     * @param name
     * @return
     */
    @RequestMapping(method = GET, value = "/home")
    public @ResponseBody Message greeting(@RequestParam(value="name", defaultValue="User") String name) {
        Message initialGreeting = new Message();
        initialGreeting.setId(UUID.randomUUID());
        initialGreeting.setContent("Welcome to Project-Durins-Folk! Sit back and have an ale!");
        initialGreeting.setFrom("Durin");
        initialGreeting.setTo(name);
        initialGreeting.setDate(new Date());
        return initialGreeting;
    }
}