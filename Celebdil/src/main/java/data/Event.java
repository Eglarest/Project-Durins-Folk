package main.java.data;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Event {

    private List<Activity> activities;
    private Date startDate;
    private Date length;
    private Address address;
    private RecurringEvent parent;
    private List<User> attendingUsers;

}