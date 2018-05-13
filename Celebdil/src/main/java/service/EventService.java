package main.java.service;

import main.java.data.DatabaseEvent;
import main.java.data.User;
import main.java.data.UserGroup;
import main.java.database.EventsDatabase;
import main.java.exception.InternalFailureException;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final int UUID_ATTEMPTS = 3;

    @Autowired
    EventsDatabase eventsDatabase;

    public DatabaseEvent getEventByEventId(UUID eventId) throws InternalFailureException {
        return eventsDatabase.readEventById(eventId);
    }

    public DatabaseEvent createNewEvent(DatabaseEvent databaseEvent) throws InternalFailureException {
        UUID uuid;
        for(int i = 0; i < UUID_ATTEMPTS; i++) {
            uuid = UUID.randomUUID();
            if(eventsDatabase.isKeyAvailable(uuid)) {
                databaseEvent.setEventId(uuid);
                eventsDatabase.writeEvent(databaseEvent);
                return databaseEvent;
            }
        }
        throw new InternalFailureException("Unable to find available Events Database Key, " +
                            "database may be getting full or retries may need to be increased.");
    }

    public List<DatabaseEvent> getEventsByUserForDate(User user, Date date) {
        DatabaseEvent databaseEvent = new DatabaseEvent();
        databaseEvent.setAddress(user.getAddress());
        databaseEvent.setActivities(new ArrayList<>());
        Date length = new Date();
        length.setTime(60000);
        databaseEvent.setLength(length);
        databaseEvent.setParent(null);
        databaseEvent.setStartDate(date);
        ArrayList<UUID> userIds = new ArrayList<>();
        userIds.add(user.getAccountNumber());
        databaseEvent.setAttendingUsers(userIds);
        ArrayList<DatabaseEvent> events = new ArrayList<>();
        events.add(databaseEvent);
        events.add(databaseEvent);
        events.add(databaseEvent);
        return events;
    }

    public List<DatabaseEvent> getEventsByUserGroupForDate(UserGroup userGroup, Date date) {
        DatabaseEvent databaseEvent = new DatabaseEvent();
        databaseEvent.setAddress(userGroup.getAddress());
        databaseEvent.setActivities(new ArrayList<>());
        Date length = new Date();
        length.setTime(60000);
        databaseEvent.setLength(length);
        databaseEvent.setParent(null);
        databaseEvent.setStartDate(date);
        ArrayList<UUID> userIds = new ArrayList<>();
        if(userGroup.getMembers() != null) {
            userGroup.getMembers().forEach(member -> userIds.add(member.getAccountNumber()));
        }
        databaseEvent.setAttendingUsers(userIds);
        ArrayList<DatabaseEvent> events = new ArrayList<>();
        events.add(databaseEvent);
        events.add(databaseEvent);
        events.add(databaseEvent);
        return events;
    }

    public boolean addUserToEvent(UUID eventId, UUID accountNumber) throws InternalFailureException {
        List<UUID> users = eventsDatabase.readUsersByEventId(eventId);
        if(!users.contains(accountNumber)) {
            users.add(accountNumber);
            eventsDatabase.updateUsersByEventId(eventId, users);
        }
        return true;
    }
}
