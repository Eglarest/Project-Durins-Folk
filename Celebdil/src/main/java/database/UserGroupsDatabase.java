package main.java.database;

import main.java.data.UserGroup;

import java.util.List;
import java.util.UUID;

public interface UserGroupsDatabase {

    UserGroup readUserGroupById(UUID uuid);

    List<UserGroup> readUserGroupByString(String string);

    boolean writeUserGroup(UserGroup userGroup);
}
