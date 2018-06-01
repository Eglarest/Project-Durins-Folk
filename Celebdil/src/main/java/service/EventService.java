package main.java.service;

import main.java.data.DatabaseEvent;
import main.java.database.EventsDatabase;
import main.java.exception.InternalFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * A service class for handling Event logic
 */
@Service
public class EventService {

    public static final int UUID_ATTEMPTS = 3;

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

    public List<DatabaseEvent> getEventsByUserIdForDate(UUID accountNumber, Date date) throws InternalFailureException {
        return eventsDatabase.readEventsByUserAndDate(accountNumber, date);
    }

    public List<DatabaseEvent> getEventsByGroupIdForDate(UUID groupId, Date date) throws InternalFailureException {
        return eventsDatabase.readEventsByGroupAndDate(groupId, date);
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
