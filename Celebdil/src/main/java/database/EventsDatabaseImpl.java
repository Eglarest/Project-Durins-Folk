package main.java.database;

import main.java.data.DatabaseEvent;
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

import static main.java.database.DatabaseTransformers.arrayToStringList;
import static main.java.database.DatabaseTransformers.formatArrayOfUUID;
import static main.java.database.DatabaseTransformers.formatNullableToStringable;
import static main.java.database.DatabaseTransformers.getAddress;
import static main.java.database.DatabaseTransformers.getJSON;
import static main.java.database.DatabaseTransformers.stringListToUUIDList;

@Repository
public class EventsDatabaseImpl implements EventsDatabase {

    @Autowired
    PostgreSQLJDBC postgreSQLJDBC;

    public static final String TABLE_NAME = "Events";
    public static final String EVENT_ID_COL = "event_id";
    public static final String NAME_COL = "name";
    public static final String DATE_COL = "date";
    public static final String LENGTH_COL = "length";
    public static final String ADDRESS_COL = "address";
    public static final String PARENT_COL = "parent";
    public static final String ATTENDEES_COL = "attendees";
    public static final String ACTIVITIES_COL = "activities";
    private static Connection connection;

    public DatabaseEvent readEventById(UUID eventId) throws InternalFailureException {
        DatabaseEvent databaseEvent = null;
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }
            ResultSet resultSet = connection.prepareCall(String.format("SELECT * FROM %s WHERE %s = '%s';",
                    TABLE_NAME, EVENT_ID_COL, eventId)).executeQuery();
            if(resultSet.next()) {
                databaseEvent = getEvent(resultSet);
            }
        } catch(SQLException | JSONException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return databaseEvent;
    }

    public List<DatabaseEvent> readEventsByUserAndDate(UUID userId, Date date) throws InternalFailureException {
        List<DatabaseEvent> databaseEventList = new ArrayList<>();
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }
            ResultSet resultSet = connection.prepareCall(String.format(
                    "SELECT * FROM %s WHERE %s @> '{\"%s\"}' AND date_trunc('day',%s) = date_trunc" +
                            "('day',TIMESTAMP WITH TIME ZONE 'epoch' + %s * INTERVAL '1 second');",
                    TABLE_NAME, ATTENDEES_COL, userId, DATE_COL, date.getTime())).executeQuery();
            while(resultSet.next()) {
                databaseEventList.add(getEvent(resultSet));
            }
        } catch(SQLException | JSONException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return databaseEventList;
    }

    public int writeEvent(DatabaseEvent databaseEvent) throws InternalFailureException {
        int writes;
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }

            List<UUID> attendingIds = databaseEvent.getAttendingUsers();
            List<UUID> activityIds = databaseEvent.getActivities();

            String query =String.format(
                    "INSERT INTO %s VALUES ('%s', '%s', to_timestamp(%s), '%s', '%s', %s, %s, %s);", TABLE_NAME, databaseEvent.getEventId(),
                    databaseEvent.getName(), databaseEvent.getStartDate().getTime(), databaseEvent.getLength().getTime(),
                    getJSON(databaseEvent.getAddress()), formatNullableToStringable(databaseEvent.getParent()), formatArrayOfUUID(attendingIds),
                    formatArrayOfUUID(activityIds));

            writes = connection.prepareCall(query).executeUpdate();
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return writes;
    }

    public int updateUsersByEventId(UUID eventId, List<UUID> userIds) throws InternalFailureException {
        int writes;
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }

            writes = connection.prepareCall(String.format(
                    "UPDATE %s SET %s = %s WHERE %s = '%s';", TABLE_NAME, ATTENDEES_COL, formatArrayOfUUID(userIds), EVENT_ID_COL, eventId)).executeUpdate();
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return writes;
    }

    public List<UUID> readUsersByEventId(UUID eventId) throws InternalFailureException {
        List<UUID> attendingUsers = new ArrayList<>();
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }
            ResultSet resultSet = connection.prepareCall(String.format("SELECT %s FROM %s WHERE %s = '%s';",
                    ATTENDEES_COL, TABLE_NAME, EVENT_ID_COL, eventId)).executeQuery();
            if(resultSet.next()) {
                attendingUsers = stringListToUUIDList(arrayToStringList(resultSet.getArray(ATTENDEES_COL)));
            }
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return attendingUsers;
    }

    public boolean isKeyAvailable(UUID eventId) throws InternalFailureException {
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }
            ResultSet resultSet = connection.prepareCall(String.format("SELECT %s FROM %s WHERE %s = '%s';",
                    EVENT_ID_COL, TABLE_NAME, EVENT_ID_COL, eventId.toString())).executeQuery();
            if(resultSet.next()) {
                return false;
            }
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return true;
    }

    private DatabaseEvent getEvent(ResultSet resultSet) throws SQLException, JSONException {
        DatabaseEvent databaseEvent = new DatabaseEvent();
        databaseEvent.setEventId(UUID.fromString(resultSet.getString(EVENT_ID_COL)));
        databaseEvent.setName(resultSet.getString(NAME_COL));
        databaseEvent.setStartDate(new Date(resultSet.getDate(DATE_COL).getTime()));
        databaseEvent.setLength(new Date(resultSet.getInt(LENGTH_COL)));
        databaseEvent.setAddress(getAddress(resultSet.getString(ADDRESS_COL)));
        String parentString = resultSet.getString(PARENT_COL);
        UUID parent = parentString == null ? null : UUID.fromString(parentString);
        databaseEvent.setParent(parent);
        List<String> attendees = arrayToStringList(resultSet.getArray(ATTENDEES_COL));
        databaseEvent.setAttendingUsers(stringListToUUIDList(attendees));
        arrayToStringList(resultSet.getArray(ACTIVITIES_COL));
        databaseEvent.setActivities(stringListToUUIDList(attendees));
        return databaseEvent;
    }
}
