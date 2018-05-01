package main.java.controller;

import main.java.data.Activity;
import main.java.data.Address;
import main.java.data.Event;
import main.java.data.User;
import main.java.data.UserGroup;
import main.java.service.ActivityService;
import main.java.service.EventService;
import main.java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static main.java.controller.ControllerConstants.ACCOUNT_NUMBER_KEY;
import static main.java.controller.ControllerConstants.ACTIVITY_NAME_KEY;
import static main.java.controller.ControllerConstants.EVENT_ID_KEY;
import static main.java.controller.ControllerConstants.EVENT_NAME_KEY;
import static main.java.controller.ControllerConstants.GROUP_ID_KEY;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    /**
     * This API will take in the user (probably through their UUID) and a range of Dates (probably must
     * be consecutive for now) and return an array containing all of the events that user currently has
     * on their calendar for those days (past -> attended, future -> planning to attend/undecided)
     * @return
     */
    @RequestMapping(method = POST, value = "/user-events")
    public @ResponseBody List<Event> getUserEvents(@RequestParam Map<String, String> allParams) {
        User user = userService.getUserByAccountNumber(UUID.fromString(allParams.get(ACCOUNT_NUMBER_KEY)));
        return eventService.getEventsByUserForDate(user, new Date());
    }

    /**
     * This API will take in the group (probably through their UUID) and a range of Dates (probably must
     * be consecutive for now) and return an array containing all of the events that group currently has
     * on their calendar for those days (past -> attended, future -> planning to attend/undecided)
     * @return
     */
    @RequestMapping(method = POST, value = "/group-events")
    public @ResponseBody List<Event> getGroupEvents(@RequestParam Map<String, String> allParams) {
        User user = new User();
        user.setTitle("Sir");
        user.setFirstName(allParams.get(GROUP_ID_KEY) + " member");
        user.setMiddleName("The");
        user.setSurName("Fearless");
        user.setSuffix("III");
        user.setAccountNumber(UUID.randomUUID());
        user.setAddress(new Address());
        UserGroup userGroup = new UserGroup();
        userGroup.addMember(user);
        userGroup.setGroupId(UUID.fromString(allParams.get(GROUP_ID_KEY)));
        return eventService.getEventsByUserGroupForDate(userGroup, new Date());
    }

    @RequestMapping(method = POST, value = "/create-event")
    public @ResponseBody Event createEvent(@RequestParam Map<String, String> allParams) {
        String eventName = allParams.get(EVENT_NAME_KEY);
        String activityName = allParams.get(ACTIVITY_NAME_KEY);
        Activity activity = activityService.getActivityByName(activityName);
        List<Activity> activities = new ArrayList<>();
        activities.add(activity);
        Date length = new Date();
        length.setTime(60000);

        Event event = new Event();
        event.setStartDate(new Date());
        event.setParent(null);
        event.setLength(length);
        event.setAddress(new Address());
        event.setActivities(activities);
        event.setName(eventName);
        event = eventService.createNewEvent(event);

        User user = userService.getUserByAccountNumber(UUID.fromString(allParams.get(ACCOUNT_NUMBER_KEY)));
        return eventService.addUserToEvent(event, user);
    }

    @RequestMapping(method = POST, value = "/join-event")
    public @ResponseBody Event joinEvent(@RequestParam Map<String,String> allParams) {
        Event event = eventService.getEventByEventId(UUID.fromString(allParams.get(EVENT_ID_KEY)));
        User user = userService.getUserByAccountNumber(UUID.fromString(allParams.get(ACCOUNT_NUMBER_KEY)));
        eventService.addUserToEvent(event, user);
        return event;
    }

}
