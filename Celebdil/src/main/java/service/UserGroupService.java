package main.java.service;

import main.java.data.UserGroup;
import main.java.database.UserGroupsDatabase;
import main.java.exception.InternalFailureException;
import main.java.exception.InvalidParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A service class for handling UserGroup logic
 */
@Service
public class UserGroupService {

    @Autowired
    private UserGroupsDatabase userGroupsDatabase;

    public static final int UUID_ATTEMPTS = 3;

    public UserGroup getUserGroupByGroupId(UUID groupId) throws InternalFailureException {
        return userGroupsDatabase.readUserGroupById(groupId);
    }

    public List<UserGroup> getUserGroupsByString(String string) throws InternalFailureException {
        return userGroupsDatabase.readUserGroupsByString(string);
    }

    public int addUserToUserGroup(UUID userGroupId, UUID userId) throws InternalFailureException, InvalidParameterException {
        UserGroup userGroup = userGroupsDatabase.readUserGroupById(userGroupId);
        if(userGroup == null) {
            throw new InvalidParameterException("UserGroup with id: " + userGroupId + " not found.");
        }
        List<UUID> members = userGroup.getMembers();
        if(members == null) {
            members = new ArrayList<>();
        }
        if(members.contains(userId)) {
            return 1;
        }
        members.add(userId);
        return userGroupsDatabase.updateMembersByGroupId(userGroupId, members);
    }

    public int addGroupOwner(UUID userGroupId, UUID userId) throws InvalidParameterException, InternalFailureException {
        UserGroup userGroup = userGroupsDatabase.readUserGroupById(userGroupId);
        if(userGroup == null) {
            throw new InvalidParameterException("UserGroup with id: " + userGroupId + " not found.");
        }
        List<UUID> owners = userGroup.getOwners();
        if(owners.contains(userId)) {
            return 1;
        }
        owners.add(userId);
        return userGroupsDatabase.updateOwnersByGroupId(userGroupId, owners);
    }

    public int removeUserFromGroup(UUID userGroupId, UUID userId) throws InternalFailureException, InvalidParameterException {
        UserGroup userGroup = userGroupsDatabase.readUserGroupById(userGroupId);
        if(userGroup == null) {
            throw new InvalidParameterException("UserGroup with id: " + userGroupId + " not found.");
        }
        List<UUID> owners = userGroup.getOwners();
        // TODO: Delete group if last owner is removed
        if(owners.size() > 1) {
            owners.remove(userId);
        }
        List<UUID> members = userGroup.getMembers();
        members.remove(userId);
        int changes = userGroupsDatabase.updateOwnersByGroupId(userGroupId, owners);
        changes += userGroupsDatabase.updateMembersByGroupId(userGroupId, members);
        return changes;
    }

    /**
     * Create a new UserGroup. We will not yet know their GroupId.
     * @param userGroup
     * @return
     */
    public UserGroup createNewUserGroup(UserGroup userGroup) throws InternalFailureException {
        UUID uuid;
        for(int i = 0; i < UUID_ATTEMPTS; i++) {
            uuid = UUID.randomUUID();
            if(userGroupsDatabase.isKeyAvailable(uuid)) {
                userGroup.setGroupId(uuid);
                userGroupsDatabase.writeUserGroup(userGroup);
                return userGroup;
            }
        }
        throw new InternalFailureException("Unable to find available UserGroups Database Key, " +
                "database may be getting full or retries may need to be increased.");
    }
}
