package main.java.activity;

import main.java.data.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class HomepageController {

    private static final String greeting = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(method = GET, value = "/home")
    public Message greeting(@RequestParam(value="name", defaultValue="User") String name) {
        Message initialGreeting = new Message();
        initialGreeting.setId(counter.incrementAndGet());
        initialGreeting.setContent("Welcome to Project-Durins-Folk! Sit back and have an ale!");
        initialGreeting.setFrom("Durin");
        initialGreeting.setTo(name);
        return initialGreeting;
    }
}