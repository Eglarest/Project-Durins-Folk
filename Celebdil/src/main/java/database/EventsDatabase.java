package main.java.database;

import main.java.data.DatabaseEvent;
import main.java.exception.InternalFailureException;

import java.util.List;
import java.util.UUID;

public interface EventsDatabase {

    DatabaseEvent readEventById(UUID eventId) throws InternalFailureException;

    int writeEvent(DatabaseEvent databaseEvent) throws InternalFailureException;

    List<UUID>  readUsersByEventId(UUID eventId) throws InternalFailureException;

    int updateUsersByEventId(UUID eventId, List<UUID> allGroupUsers) throws InternalFailureException;

    boolean isKeyAvailable(UUID uuid) throws InternalFailureException;
}
