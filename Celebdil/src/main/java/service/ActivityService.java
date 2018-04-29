package main.java.service;

import main.java.data.Activity;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    public Activity getActivityByName(String name) {
        Activity activity = new Activity();
        activity.setName(name);
        activity.setType("dummy");
        return activity;
    }
}
