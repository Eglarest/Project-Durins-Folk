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
    //TODO: Add Description

    public void addMember(User user) {
        if(members == null) {
            members = new ArrayList<>();
        }
        if(!members.contains(user)) {
            members.add(user);
        }
    }

    public void addSuccessor(User user) {
        if(successors == null) {
            successors = new ArrayList<>();
        }
        if(!successors.contains(user)) {
            successors.add(user);
        }
    }

    public void removeMember(User user) {
        if(members != null) {
            members.remove(user);
        }
    }

    public void removeSuccessor(User user) {
        if(successors != null) {
            successors.remove(user);
        }
    }

    public User succeedLeader() {
        leader = null;
        if(successors != null && successors.size() > 0) {
            int successor = (int)(Math.random()*successors.size());
            leader = successors.get(successor);
            successors.remove(successor);
        }
        return leader;
    }
}
