package main.java.service;

import main.java.data.Event;
import main.java.data.User;
import main.java.data.UserGroup;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * A service class for handling Event logic
 */
@Service
public class EventService {

    public Event getEventByEventId(UUID eventId) {
        Event event = new Event();
        event.setStartDate(new Date());
        event.setName("Dummy Test");
        event.setEventId(eventId);
        return event;
    }

    public Event createNewEvent(Event event) {
        event.setEventId(UUID.randomUUID());
        return event;
    }

    public List<Event> getEventsByUserForDate(User user, Date date) {
        Event event = new Event();
        event.setAddress(user.getAddress());
        event.setActivities(new ArrayList<>());
        Date length = new Date();
        length.setTime(60000);
        event.setLength(length);
        event.setParent(null);
        event.setStartDate(date);
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        event.setAttendingUsers(users);
        ArrayList<Event> events = new ArrayList<>();
        events.add(event);
        events.add(event);
        events.add(event);
        return events;
    }

    public List<Event> getEventsByUserGroupForDate(UserGroup userGroup, Date date) {
        Event event = new Event();
        event.setAddress(userGroup.getAddress());
        event.setActivities(new ArrayList<>());
        Date length = new Date();
        length.setTime(60000);
        event.setLength(length);
        event.setParent(null);
        event.setStartDate(date);
        ArrayList<User> users = new ArrayList<>(userGroup.getMembers());
        event.setAttendingUsers(users);
        ArrayList<Event> events = new ArrayList<>();
        events.add(event);
        events.add(event);
        events.add(event);
        return events;
    }

    public Event addUserToEvent(Event event, User user) {
        event.addAttendingUser(user);
        return event;
    }
}
