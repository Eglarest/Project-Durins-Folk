package main.java.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class UserGroup {

    private String name;
    private UUID groupId;
    private User leader;
    private List<User> successors;
    private Address address;
    private List<User> members;
    private Date creationDate;

    public void addMember(User user) {
        if(members == null) {
            members = new ArrayList<>();
        }
        members.add(user);
    }

}
