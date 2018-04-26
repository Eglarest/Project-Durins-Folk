package activity;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import data.Message;

@RestController
public class HomepageController {

    private static final String greeting = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/home")
    public Message greeting(@RequestParam(value="name", defaultValue="User") String name) {
        return new Message(counter.incrementAndGet(), String.format(greeting, name), "Keegan", "Anna");
    }
}