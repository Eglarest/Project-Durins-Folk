package main.java.database;

import main.java.data.Activity;

public interface ActivitiesDatabase {

    Activity readActivityByName(String name);

    boolean writeActivity(String name, String type);
}
