package main.java.data;

import lombok.Data;

import java.util.UUID;

@Data
public class UserGroup {

    private String name;
    private UUID groupId;
    private User leader;
    private User[] successor;
    private Address address;

}
