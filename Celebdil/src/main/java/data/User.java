package main.java.data;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class User {

    private String title;
    private String firstName;
    private String middleName;
    private String surName;
    private String suffix;
    private UUID accountNumber;
    private Address address;
    private Date joinDate;

}
