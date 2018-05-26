package main.java.database;

import main.java.data.User;
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
import static main.java.database.DatabaseTransformers.formatNullableToStringable;
import static main.java.database.DatabaseTransformers.getAddress;
import static main.java.database.DatabaseTransformers.getJSON;

@Repository
public class UsersDatabaseImpl implements UsersDatabase {

    @Autowired
    PostgreSQLJDBC postgreSQLJDBC;

    public static final String TABLE_NAME = "users";
    public static final String ACCOUNT_ID_COL = "account_id";
    public static final String TITLE_COL = "title";
    public static final String FIRST_NAME_COL = "first_name";
    public static final String MIDDLE_NAME_COL = "middle_name";
    public static final String SURNAME_COL = "surname";
    public static final String SUFFIX_COL = "suffix";
    public static final String ADDRESS_COL = "address";
    public static final String JOIN_DATE_COL = "join_date";
    private static Connection connection;

    public User readUserById(UUID accountId) throws InternalFailureException {
        User user = null;
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }
            ResultSet resultSet = connection.prepareCall(String.format("SELECT * FROM %s WHERE %s = '%s';",
                    TABLE_NAME, ACCOUNT_ID_COL, accountId)).executeQuery();
            if(resultSet.next()) {
                user = getUser(resultSet);
            }
        } catch(SQLException | JSONException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return user;
    }

    public List<User> readUsersByString(String string) throws InternalFailureException {
        List<User> users = new ArrayList<>();
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }

            String withWilds = addWildcards(string);

            ResultSet resultSet = connection.prepareCall(String.format("SELECT * FROM %s WHERE (%s like '%s' OR %s like '%s' OR " +
                    "%s like '%s' OR %s like '%s' OR %s like '%s');", TABLE_NAME, TITLE_COL, withWilds, FIRST_NAME_COL, withWilds,
                    MIDDLE_NAME_COL, withWilds, SURNAME_COL, withWilds, SUFFIX_COL, withWilds)).executeQuery();
            while(resultSet.next()) {
                users.add(getUser(resultSet));
            }
        } catch(SQLException | JSONException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return users;
    }

    public int writeUser(User user) throws InternalFailureException {
        int writes;
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }

            String query = String.format(
                    "INSERT INTO %s VALUES ('%s', %s, '%s', %s, '%s', %s, '%s', to_timestamp(%s));", TABLE_NAME,
                    user.getAccountNumber(), formatNullableToStringable(user.getTitle()), user.getFirstName(),
                    formatNullableToStringable(user.getMiddleName()), user.getSurname(), formatNullableToStringable(user.getSuffix()),
                    getJSON(user.getAddress()), user.getJoinDate().getTime());

            writes = connection.prepareCall(query).executeUpdate();
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return writes;
    }

    public boolean isKeyAvailable(UUID accountId) throws InternalFailureException {
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }
            ResultSet resultSet = connection.prepareCall(String.format("SELECT * FROM %s WHERE %s = '%s';",
                    TABLE_NAME, ACCOUNT_ID_COL, accountId)).executeQuery();
            if(resultSet.next()) {
                return false;
            }
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return true;
    }

    private User getUser(ResultSet resultSet) throws SQLException, JSONException {
        User user = new User();
        user.setAccountNumber(UUID.fromString(resultSet.getString(ACCOUNT_ID_COL)));
        user.setTitle(resultSet.getString(TITLE_COL));
        user.setFirstName(resultSet.getString(FIRST_NAME_COL));
        user.setMiddleName(resultSet.getString(MIDDLE_NAME_COL));
        user.setSurname(resultSet.getString(SURNAME_COL));
        user.setSuffix(resultSet.getString(SUFFIX_COL));
        user.setAddress(getAddress(resultSet.getString(ADDRESS_COL)));
        user.setJoinDate(new Date(resultSet.getDate(JOIN_DATE_COL).getTime()));
        return user;
    }

}
