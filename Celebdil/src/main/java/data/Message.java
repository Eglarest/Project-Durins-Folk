package main.java.data;

import lombok.Data;

import java.util.UUID;

@Data
public class Message {

    private UUID id;
    private String content;
    private String from;
    private String to;

}