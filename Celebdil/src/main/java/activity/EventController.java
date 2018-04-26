package main.java.activity;

import main.java.data.Address;
import main.java.data.Event;
import main.java.data.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class EventController {

    @RequestMapping(method = GET, value = "/events")
    public Event[] getUserEvents(@RequestParam(value="name", defaultValue="User") String name) {
        User user = new User();
        user.setTitle("Sir");
        user.setFirstName(name);
        user.setMiddleName("The");
        user.setSurName("Fearless");
        user.setSuffix("III");
        user.setAccountNumber(UUID.randomUUID());
        Address address = new Address();
        user.setAddress(address);
        Event event = new Event();
        event.setAddress(address);
        event.setActivities(null);
        event.setLength(new Date());
        event.setParent(null);
        event.setStartDate(new Date());
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        event.setAttendingUsers(users);
        Event[] events = {event, event, event};
        return events;
    }
}
