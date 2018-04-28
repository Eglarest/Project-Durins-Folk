package main.java.data;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RecurringEvent {

    private List<Activity> activities;
    private Date startdate;
    private Date length;
    private Date frequency;
    private Date endDate;
    private List<Event> children;
    private Address address;

}
