package main.java.service;

import main.java.data.Activity;
import main.java.database.ActivitiesDatabase;
import main.java.exception.InternalFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    @Autowired
    ActivitiesDatabase activitiesDatabase;

    public Activity getActivityByName(String name) throws InternalFailureException {
        List<Activity> possibleActivities = activitiesDatabase.readActivitiesByName(name);
        if(possibleActivities.size() != 0) {
            return possibleActivities.get(0);
        }
        return null;
    }

    public List<Activity> findActivitiesByType(String type) throws InternalFailureException {
        return activitiesDatabase.readActivitiesLikeType(type);
    }

    public List<Activity> findActivitiesByName(String name) throws InternalFailureException {
        return activitiesDatabase.readActivitiesLikeType(name);
    }

    public List<Activity> findActivitiesByString(String string) throws InternalFailureException {
        return activitiesDatabase.readActivitiesLikeString(string);
    }

    public int createActivity(Activity activity) throws InternalFailureException {
        return activitiesDatabase.writeActivity(activity);
    }
}
