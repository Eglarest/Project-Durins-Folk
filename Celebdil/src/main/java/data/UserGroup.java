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
    private List<UUID> owners;
    private Address address;
    private List<UUID> members;
    private Date creationDate;
    //TODO: Add Description

    public void addMember(UUID userId) {
        if(members == null) {
            members = new ArrayList<>();
        }
        if(!members.contains(userId)) {
            members.add(userId);
        }
    }

    public void addOwner(UUID userId) {
        if(owners == null) {
            owners = new ArrayList<>();
        }
        if(!owners.contains(userId)) {
            owners.add(userId);
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
