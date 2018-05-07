package main.java.database;

import main.java.data.Message;
import main.java.exception.InternalFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class MessagesDatabaseImpl implements MessagesDatabase {

    @Autowired
    PostgreSQLJDBC postgreSQLJDBC;

    public static final String TABLE_NAME = "Messages";
    public static final String MESSAGE_ID_COL = "message_id";
    public static final String CONTENT_COL = "content";
    public static final String DATE_COL = "date";
    public static final String TO_USER_COL = "to_user";
    public static final String FROM_USER_COL = "from_user";
    private static Connection connection;

    public List<Message> readMessagesTo(UUID uuid) throws InternalFailureException {
        List<Message> messages = new ArrayList<>();
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }
            ResultSet resultSet = connection.prepareCall(String.format("SELECT * FROM %s WHERE %s = '%s' ORDER BY date DESC;",
                    TABLE_NAME, TO_USER_COL, uuid)).executeQuery();
            while(resultSet.next()) {
                messages.add(getMessage(resultSet));
            }
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return messages;
    }

    public List<Message> readMessagesFrom(UUID uuid) throws InternalFailureException {
        List<Message> messages = new ArrayList<>();
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }
            ResultSet resultSet = connection.prepareCall(String.format("SELECT * FROM %s WHERE %s = '%s' ORDER BY date DESC;",
                                                                        TABLE_NAME, FROM_USER_COL, uuid)).executeQuery();
            while(resultSet.next()) {
                messages.add(getMessage(resultSet));
            }
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return messages;
    }

    public List<Message> readMessagesBetween(UUID uuid1, UUID uuid2) throws InternalFailureException {
        List<Message> messages = new ArrayList<>();
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }
            ResultSet resultSet = connection.prepareCall(String.format(
                    "SELECT * FROM %s WHERE (%s = '%s' AND %s = '%s') OR (%s = '%s' AND %s = '%s') ORDER BY date DESC;",
                    TABLE_NAME, TO_USER_COL, uuid1, FROM_USER_COL, uuid2, TO_USER_COL, uuid2, FROM_USER_COL, uuid1)).executeQuery();
            while(resultSet.next()) {
                messages.add(getMessage(resultSet));
            }
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return messages;
    }

    public Message readMessageById(UUID uuid) throws InternalFailureException {
        Message message = null;
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }
            ResultSet resultSet = connection.prepareCall(String.format("SELECT * FROM %s WHERE %s = '%s';",
                                                                        TABLE_NAME, MESSAGE_ID_COL, uuid)).executeQuery();
            if(resultSet.next()) {
                message = getMessage(resultSet);
            }
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return message;
    }

    public int writeMessage(Message message) throws InternalFailureException {
        int inserts;
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }
             inserts = connection.prepareCall(String.format("INSERT INTO %s VALUES ('%s', '%s', '%s', '%s', '%s');",
                    TABLE_NAME, message.getId(), message.getContent(), message.getDate(), message.getTo(), message.getFrom())).executeUpdate();
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return inserts;
    }

    private Message getMessage(ResultSet resultSet) throws SQLException {
        Message message = new Message();
        message.setId(UUID.fromString(resultSet.getString(MESSAGE_ID_COL)));
        message.setContent(resultSet.getString(CONTENT_COL));
        message.setTo(UUID.fromString(resultSet.getString(TO_USER_COL)));
        message.setFrom(UUID.fromString(resultSet.getString(FROM_USER_COL)));
        message.setDate(resultSet.getTimestamp(DATE_COL));
        return message;
    }
}
