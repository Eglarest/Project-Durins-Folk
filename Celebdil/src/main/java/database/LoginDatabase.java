package main.java.database;

import main.java.data.LoginCredentials;
import main.java.exception.InternalFailureException;
import main.java.exception.InvalidLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

//TODO: THIS WHOLE DAMN CLASS IS TEMPORARY
@Repository
public class LoginDatabase {

    @Autowired
    private PostgreSQLJDBC postgreSQLJDBC;

    public static final String TABLE_NAME = "Login";
    public static final String ACCOUNT_ID_COL = "account_id";
    public static final String USERNAME_COL = "username";
    public static final String PASSWORD_COL = "password";
    private static Connection connection;

    public LoginCredentials readLoginByUsername(String username) throws InternalFailureException, InvalidLoginException {
        LoginCredentials loginCredentials;
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }
            ResultSet resultSet = connection.prepareCall(String.format("SELECT * FROM %s WHERE %s = '%s';",
                    TABLE_NAME, USERNAME_COL, username)).executeQuery();
            if(resultSet.next()) {
                loginCredentials = new LoginCredentials();
                loginCredentials.setUsername(resultSet.getString(USERNAME_COL));
                loginCredentials.setPassword(resultSet.getString(PASSWORD_COL));
                String accountId = resultSet.getString(ACCOUNT_ID_COL);
                loginCredentials.setAccountId(UUID.fromString(accountId));
            } else {
                throw new InvalidLoginException("Incorrect username or password.");
            }
        }catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return loginCredentials;
    }

    public int writeNewUser(String username, String password) throws InternalFailureException {
        int writes;
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }

            String query = String.format(
                    "INSERT INTO %s VALUES ('%s', '%s');", TABLE_NAME, username, password);

            writes = connection.prepareCall(query).executeUpdate();
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return writes;
    }

    public int updatePassword(String username, String password) throws InternalFailureException {
        int writes;
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }

            writes = connection.prepareCall(String.format(
                    "UPDATE %s SET %s = %s WHERE %s = '%s';", TABLE_NAME, PASSWORD_COL, password, USERNAME_COL, username)).executeUpdate();

        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return writes;
    }
}
