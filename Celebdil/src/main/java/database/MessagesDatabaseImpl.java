package main.java.database;

import main.java.data.Message;
import main.java.exception.InternalFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public List<Message> readMessagesTo(UUID uuid) {
        return null;
    }

    public List<Message> readMessagesFrom(UUID uuid) {
        return null;
    }

    public Message readMessageById(UUID uuid) throws InternalFailureException {
        Message message = null;
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }
            ResultSet resultSet = connection.prepareCall(String.format("SELECT * FROM %s WHERE message_id = '%s';",TABLE_NAME,uuid)).executeQuery();
            if(resultSet.next()) {
                message = new Message();
                message.setId(UUID.fromString(resultSet.getString(MESSAGE_ID_COL)));
                message.setContent(resultSet.getString(CONTENT_COL));
                message.setTo(UUID.fromString(resultSet.getString(TO_USER_COL)));
                message.setFrom(UUID.fromString(resultSet.getString(FROM_USER_COL)));
                message.setDate(resultSet.getTimestamp(DATE_COL));
            }
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return message;
    }

    public boolean writeMessage(Message message) {
        return false;
    }
}
