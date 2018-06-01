package main.java.database;

import main.java.data.UserGroup;
import main.java.exception.InternalFailureException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static main.java.database.DatabaseTransformers.addWildcards;
import static main.java.database.DatabaseTransformers.arrayToStringList;
import static main.java.database.DatabaseTransformers.formatArrayOfUUID;
import static main.java.database.DatabaseTransformers.getAddress;
import static main.java.database.DatabaseTransformers.getJSON;
import static main.java.database.DatabaseTransformers.stringListToUUIDList;

@Repository
public class UserGroupsDatabaseImpl implements UserGroupsDatabase{

    @Autowired
    private PostgreSQLJDBC postgreSQLJDBC;

    public static final String TABLE_NAME = "userGroups";
    public static final String GROUP_ID_COL = "group_id";
    public static final String NAME_COL = "name";
    public static final String CREATION_DATE_COL = "creation_date";
    public static final String ADDRESS_COL = "address";
    public static final String OWNERS_COL = "owners";
    public static final String MEMBERS_COL = "members";
    private static Connection connection;

    public UserGroup readUserGroupById(UUID groupId) throws InternalFailureException {
        UserGroup userGroup = null;
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }
            ResultSet resultSet = connection.prepareCall(String.format("SELECT * FROM %s WHERE %s = '%s';",
                    TABLE_NAME, GROUP_ID_COL, groupId)).executeQuery();
            if(resultSet.next()) {
                userGroup = getUserGroup(resultSet);
            }
        } catch(SQLException | JSONException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return userGroup;
    }

    public List<UserGroup> readUserGroupsByString(String string) throws InternalFailureException {
        List<UserGroup> userGroups = new ArrayList<>();
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }

            String withWilds = addWildcards(string);

            ResultSet resultSet = connection.prepareCall(String.format("SELECT * FROM %s WHERE %s like '%s';",
                    TABLE_NAME, NAME_COL, withWilds)).executeQuery();
            while(resultSet.next()) {
                userGroups.add(getUserGroup(resultSet));
            }
        } catch(SQLException | JSONException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return userGroups;
    }

    public int updateOwnersByGroupId(UUID groupId, List<UUID> owners) throws InternalFailureException {
        int writes;
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }

            writes = connection.prepareCall(String.format(
                    "UPDATE %s SET %s = %s WHERE %s = '%s';", TABLE_NAME, MEMBERS_COL, formatArrayOfUUID(owners), GROUP_ID_COL, groupId))
                    .executeUpdate();
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return writes;
    }

    public int updateMembersByGroupId(UUID groupId, List<UUID> members) throws InternalFailureException {
        int writes;
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }

            writes = connection.prepareCall(String.format(
                    "UPDATE %s SET %s = %s WHERE %s = '%s';", TABLE_NAME, MEMBERS_COL, formatArrayOfUUID(members), GROUP_ID_COL, groupId))
                    .executeUpdate();
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return writes;
    }

    public int writeUserGroup(UserGroup userGroup) throws InternalFailureException {
        int writes;
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }

            String query = String.format(
                    "INSERT INTO %s VALUES ('%s', '%s', to_timestamp(%s), '%s', %s, %s);", TABLE_NAME, userGroup.getGroupId(),
                    userGroup.getName(), userGroup.getCreationDate().getTime(), getJSON(userGroup.getAddress()),
                    formatArrayOfUUID(userGroup.getOwners()), formatArrayOfUUID(userGroup.getOwners()));

            writes = connection.prepareCall(query).executeUpdate();
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return writes;
    }

    public boolean isKeyAvailable(UUID key) throws InternalFailureException {
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }
            ResultSet resultSet = connection.prepareCall(String.format("SELECT * FROM %s WHERE %s = '%s';",
                    TABLE_NAME, GROUP_ID_COL, key)).executeQuery();
            if(resultSet.next()) {
                return false;
            }
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return true;
    }

    private UserGroup getUserGroup(ResultSet resultSet) throws SQLException, JSONException {
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupId(UUID.fromString(resultSet.getString(GROUP_ID_COL)));
        userGroup.setName(resultSet.getString(NAME_COL));
        List<String> members = arrayToStringList(resultSet.getArray(MEMBERS_COL));
        userGroup.setMembers(stringListToUUIDList(members));
        List<String> owners = arrayToStringList(resultSet.getArray(OWNERS_COL));
        userGroup.setOwners(stringListToUUIDList(owners));
        userGroup.setAddress(getAddress(resultSet.getString(ADDRESS_COL)));
        userGroup.setCreationDate(new Date(resultSet.getDate(CREATION_DATE_COL).getTime()));
        return userGroup;
    }
}
