package main.java.activity;

import main.java.data.Address;
import main.java.data.Event;
import main.java.data.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class EventController {

    // TODO: This will be a POST Request once we have the infrastructure in place to handle it
    // (we are leaving it as available as possible for frontend development)
    /**
     * This API will take in the user (probably through their UUID) and a range of Dates (probably must
     * be consecutive for now) and return an array containing all of the events that user currently has
     * on their calendar for those days (past -> attended, future -> planning to attend/undecided)
     * @param name
     * @return
     */
    @RequestMapping(method = GET, value = "/user-events")
    public @ResponseBody Event[] getUserEvents(@RequestParam(value="name", defaultValue="User") String name) {
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

    // TODO: This will be a POST Request once the infrastructure is in place to handle it
    // (we are leaving it as available as possible for frontend development)
    /**
     * This API will take in the group (probably through their UUID) and a range of Dates (probably must
     * be consecutive for now) and return an array containing all of the events that group currently has
     * on their calendar for those days (past -> attended, future -> planning to attend/undecided)
     * @param group
     * @return
     */
    @RequestMapping(method = GET, value = "/group-events")
    public @ResponseBody Event[] getGroupEvents(@RequestParam(value="group", defaultValue="User") String group) {
        User user = new User();
        user.setTitle("Sir");
        user.setFirstName(group + " members");
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
