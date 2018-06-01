package main.java.database;

import main.java.data.UserGroup;
import main.java.exception.InternalFailureException;

import java.util.List;
import java.util.UUID;

public interface UserGroupsDatabase {

    UserGroup readUserGroupById(UUID uuid) throws InternalFailureException;

    List<UserGroup> readUserGroupsByString(String string) throws InternalFailureException;

    int writeUserGroup(UserGroup userGroup) throws InternalFailureException;

    int updateOwnersByGroupId(UUID groupId, List<UUID> owners) throws InternalFailureException;

    int updateMembersByGroupId(UUID groupId, List<UUID> members) throws InternalFailureException;

    boolean isKeyAvailable(UUID key) throws InternalFailureException;
}
