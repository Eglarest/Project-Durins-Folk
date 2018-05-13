package main.java.database;

import main.java.data.Activity;
import main.java.exception.InternalFailureException;

import java.util.List;
import java.util.UUID;

public interface ActivitiesDatabase {

    Activity readActivityById(UUID activityId) throws InternalFailureException;

    List<Activity> readActivitiesByName(String name) throws InternalFailureException;

    List<Activity> readActivitiesLikeString(String string) throws InternalFailureException;

    List<Activity> readActivitiesLikeName(String name) throws InternalFailureException;

    List<Activity> readActivitiesLikeType(String type) throws InternalFailureException;

    int writeActivity(Activity activity) throws InternalFailureException;

}
