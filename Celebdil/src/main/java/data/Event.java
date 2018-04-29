package main.java.data;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class Event {

    private String name;
    private List<Activity> activities;
    private Date startDate;
    private Date length;
    private Address address;
    private RecurringEvent parent;
    private List<User> attendingUsers;
    private UUID eventId;

}