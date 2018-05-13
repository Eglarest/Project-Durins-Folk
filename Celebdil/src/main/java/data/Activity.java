package main.java.data;

import lombok.Data;

import java.util.UUID;

@Data
public class Activity {

    private UUID activityId;
    private String name;
    private String type;

}