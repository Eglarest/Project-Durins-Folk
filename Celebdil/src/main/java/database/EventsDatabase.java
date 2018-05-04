package main.java.database;

import main.java.data.Event;

import java.util.UUID;

public interface EventsDatabase {

    Event readEventById(UUID uuid);

    boolean writeEvent(Event event);
}
