package main.java.data;

import lombok.Data;

@Data
public class Message {

    private long id;
    private String content;
    private String from;
    private String to;

}