package main.java.service;

import main.java.data.Address;
import main.java.data.User;
import main.java.data.UserGroup;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * A service class for handling UserGroup logic
 */
@Service
public class UserGroupService {

    public UserGroup getUserGroupByGroupId(UUID groupId) {
        UserGroup usergroup = new UserGroup();
        usergroup.setGroupId(groupId);
        usergroup.setAddress(new Address());
        usergroup.setCreationDate(new Date());
        usergroup.setName("Thrain's sons");
        return usergroup;
    }

    public List<UserGroup> getUserGroupsByString(String string) {
        UserGroup userGroup= getUserGroupByGroupId(UUID.randomUUID());
        List<UserGroup> userGroups = new ArrayList<>();
        userGroups.add(userGroup);
        userGroups.add(userGroup);
        userGroups.add(userGroup);
        return userGroups;
    }

    public boolean addUserToUserGroup(UserGroup userGroup, UUID userId) {
        userGroup.addMember(userId);
        return true;
    }

    public List<UUID> addGroupOwner(UserGroup userGroup, UUID userId) {
        userGroup.addOwner(userId);
        return userGroup.getOwners();
    }

    public boolean removeUserFromGroup(UserGroup userGroup, User user) {
        userGroup.removeMember(user);
        userGroup.removeOwner(user);
        userGroup.getOwners();
        if(userGroup.getOwners() == null || userGroup.getOwners().size() == 0) {
            // TODO: Delete group
            return false;
        }
        return true;
    }

    /**
     * Create a new UserGroup. We will not yet know their GroupId.
     * @param userGroup
     * @return
     */
    public UserGroup createNewUserGroup(UserGroup userGroup) {
        userGroup.setGroupId(UUID.randomUUID());
        return userGroup;
    }
}
