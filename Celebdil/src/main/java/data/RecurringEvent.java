package main.java.data;

import lombok.Data;

import java.util.Date;

@Data
public class RecurringEvent {

    private Activity[] activities;
    private Date startdate;
    private Date length;
    private Date frequency;
    private Date endDate;
    private Event[] children;
    private Address address;

}
