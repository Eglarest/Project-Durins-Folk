package main.java.data;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Message {

    private UUID id;
    private String content;
    private UUID from;
    private UUID to;
    private Date date;

}