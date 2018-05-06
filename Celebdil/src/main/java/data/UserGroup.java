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
    private List<User> owners;
    private Address address;
    private List<User> members;
    private Date creationDate;
    //TODO: Add Description

    public void addMember(User user) {
        if(members == null) {
            members = new ArrayList<>();
        }
        if(!members.contains(user)) {
            members.add(user);
        }
    }

    public void addOwner(User user) {
        if(owners == null) {
            owners = new ArrayList<>();
        }
        if(!owners.contains(user)) {
            owners.add(user);
        }
    }

    public void removeMember(User user) {
        if(members != null) {
            members.remove(user);
        }
    }

    public void removeOwner(User user) {
        if(owners != null) {
            owners.remove(user);
        }
    }
}
