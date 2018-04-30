package main.java.service;

import main.java.data.Activity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityService {

    public Activity getActivityByName(String name) {
        Activity activity = new Activity();
        activity.setName(name);
        activity.setType("dummy");
        return activity;
    }

    public List<Activity> getActivitiesByType(String type) {
        Activity activity = new Activity();
        activity.setType(type);
        activity.setName("dummy");
        List<Activity> activities = new ArrayList<>();
        activities.add(activity);
        return activities;
    }

    public List<Activity> findActivitiesByType(String string) {
        Activity activity = new Activity();
        activity.setType(string + " ish");
        activity.setName("dummy");
        List<Activity> activities = new ArrayList<>();
        activities.add(activity);
        return activities;
    }

    public List<Activity> findActivitiesByName(String string) {
        Activity activity = new Activity();
        activity.setName(string + " ish");
        activity.setType("dummy");
        List<Activity> activities = new ArrayList<>();
        activities.add(activity);
        return activities;
    }
}
