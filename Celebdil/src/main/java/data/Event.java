package main.java.data;

import lombok.Data;

import java.util.Date;

@Data
public class Event {

    private Activity[] activities;
    private Date startDate;
    private Date length;
    private Address address;
    private RecurringEvent parent;

}