package main.java.data;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class DatabaseEvent {

    //TODO: We are renaming this "Event" when I've finished converting all uses of Event
    private String name;
    private List<UUID> activities;
    private Date startDate;
    private Date length;
    private Address address;
    private UUID parent;
    private List<UUID> attendingUsers;
    private UUID eventId;

}
