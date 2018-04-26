package main.java.data;

import lombok.Data;

import java.util.Date;

@Data
public class Contact {

    private User user1;
    private User user2;
    private Date firstContact;
    private Date lastContact;
    private Event[] sharedEvents;

}
