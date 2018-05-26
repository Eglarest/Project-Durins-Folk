package main.java.database;

import main.java.data.Activity;
import main.java.exception.InternalFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static main.java.database.DatabaseTransformers.addWildcards;

@Repository
public class ActivitiesDatabaseImpl implements ActivitiesDatabase{

    @Autowired
    PostgreSQLJDBC postgreSQLJDBC;

    public static final String TABLE_NAME = "Events";
    public static final String ACTIVITY_ID_COL = "activity_id";
    public static final String NAME_COL = "name";
    public static final String TYPE_COL = "type";
    private static Connection connection;

    public Activity readActivityById(UUID activityId) throws InternalFailureException {
        Activity activity = null;
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }
            ResultSet resultSet = connection.prepareCall(String.format("SELECT * FROM %s WHERE %s = '%s';",
                    TABLE_NAME, ACTIVITY_ID_COL, activityId)).executeQuery();
            if(resultSet.next()) {
                activity = getActivity(resultSet);
            }
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return activity;
    }

    public List<Activity> readActivitiesByName(String name) throws InternalFailureException {
        List<Activity> activityList = new ArrayList<>();
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }
            ResultSet resultSet = connection.prepareCall(String.format("SELECT * FROM %s WHERE %s = '%s';",
                    TABLE_NAME, NAME_COL, name)).executeQuery();
            while(resultSet.next()) {
                activityList.add(getActivity(resultSet));
            }
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return activityList;
    }

    public List<Activity> readActivitiesLikeString(String string) throws InternalFailureException {
        List<Activity> activityList = new ArrayList<>();
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }
            ResultSet resultSet = connection.prepareCall(String.format("SELECT * FROM %s WHERE %s like '%s' OR %s like '%s';",
                    TABLE_NAME, NAME_COL, addWildcards(string), TYPE_COL, addWildcards(string))).executeQuery();
            while(resultSet.next()) {
                activityList.add(getActivity(resultSet));
            }
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return activityList;
    }

    public List<Activity> readActivitiesLikeName(String name) throws InternalFailureException {
        List<Activity> activityList = new ArrayList<>();
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }
            ResultSet resultSet = connection.prepareCall(String.format("SELECT * FROM %s WHERE %s like '%s';",
                    TABLE_NAME, NAME_COL, addWildcards(name))).executeQuery();
            while(resultSet.next()) {
                activityList.add(getActivity(resultSet));
            }
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return activityList;
    }

    public List<Activity> readActivitiesLikeType(String type) throws InternalFailureException {
        List<Activity> activityList = new ArrayList<>();
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }
            ResultSet resultSet = connection.prepareCall(String.format("SELECT * FROM %s WHERE %s like '%s';",
                    TABLE_NAME, TYPE_COL, addWildcards(type))).executeQuery();
            while(resultSet.next()) {
                activityList.add(getActivity(resultSet));
            }
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return activityList;
    }

    public int writeActivity(Activity activity) throws InternalFailureException {
        int writes;
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }

            String query = String.format(
                    "INSERT INTO %s VALUES ('%s', '%s','%s');",
                    TABLE_NAME, activity.getActivityId(), activity.getName(), activity.getType());

            writes = connection.prepareCall(query).executeUpdate();
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return writes;
    }

    private Activity getActivity(ResultSet resultSet) throws SQLException {
        Activity activity = new Activity();
        activity.setActivityId(UUID.fromString(resultSet.getString(ACTIVITY_ID_COL)));
        activity.setName(resultSet.getString(NAME_COL));
        activity.setType(resultSet.getString(TYPE_COL));
        return activity;
    }
}
