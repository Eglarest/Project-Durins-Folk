package main.java.data;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Contact {

    private User user1;
    private User user2;
    private Date firstContact;
    private Date lastContact;
    private List<Event> sharedEvents;
    private short status; // 0: active 1: pending lower 2: pending higher 3: blocked by lower 4: blocked by higher 5: blocked by both

}
