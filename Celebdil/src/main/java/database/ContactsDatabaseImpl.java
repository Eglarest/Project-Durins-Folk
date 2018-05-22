package main.java.database;

import main.java.data.Contact;
import main.java.data.Contact.ContactStatus;
import main.java.exception.InternalFailureException;
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
import static main.java.database.DatabaseTransformers.formatNullableDate;
import static main.java.database.DatabaseTransformers.stringListToUUIDList;

@Repository
public class ContactsDatabaseImpl implements ContactsDatabase {

    @Autowired
    PostgreSQLJDBC postgreSQLJDBC;

    public static final String TABLE_NAME = "Contacts";
    public static final String LOWER_ACCOUNT_COL = "lower_account_id";
    public static final String HIGHER_ACCOUNT_COL = "higher_account_id";
    public static final String FIRST_CONTACT_COL = "first_contact";
    public static final String LAST_CONTACT_COL = "last_contact";
    public static final String SHARED_EVENTS_COL = "shared_events";
    public static final String STATUS_COL = "contactStatus";
    public static Connection connection;

    public List<Contact> readContactsByUserId(UUID userId) throws InternalFailureException {
        List<Contact> contactList = new ArrayList<>();
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }
            ResultSet resultSet = connection.prepareCall(String.format("SELECT * FROM %s WHERE %s = '%s' OR %s = '%s';",
                    TABLE_NAME, LOWER_ACCOUNT_COL, userId, HIGHER_ACCOUNT_COL, userId)).executeQuery();
            while(resultSet.next()) {
                contactList.add(getContact(resultSet));
            }
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return contactList;
    }

    public Contact readContactPair(UUID lowerUserId, UUID higherUserId) throws InternalFailureException {
        Contact contact = null;
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }
            ResultSet resultSet = connection.prepareCall(String.format("SELECT * FROM %s WHERE %s = '%s' OR %s = '%s';",
                    TABLE_NAME, LOWER_ACCOUNT_COL, lowerUserId, HIGHER_ACCOUNT_COL, higherUserId)).executeQuery();
            if(resultSet.next()) {
                contact = getContact(resultSet);
            }
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return contact;
    }

    public int writeContact(Contact contact) throws InternalFailureException {
        int writes;
        try {
            if(connection == null || connection.isClosed()) {
                connection = postgreSQLJDBC.getConnection();
            }

            List<UUID> sharedEvents = contact.getSharedEventIds();

            String query = String.format(
                    "INSERT INTO %s VALUES ('%s', '%s', %s, %s, %s, '%s');", TABLE_NAME, contact.getUser1(),
                    contact.getUser2(), formatNullableDate(contact.getFirstContact()), formatNullableDate(contact.getLastContact()),
                    formatArrayOfUUID(sharedEvents), contact.getContactStatus().ordinal());

            writes = connection.prepareCall(query).executeUpdate();
        } catch(SQLException e) {
            throw new InternalFailureException(e.getMessage());
        }
        return writes;
    }

    private Contact getContact(ResultSet resultSet) throws SQLException {
        Contact contact = new Contact();
        contact.setUser1(UUID.fromString(resultSet.getString(LOWER_ACCOUNT_COL)));
        contact.setUser1(UUID.fromString(resultSet.getString(HIGHER_ACCOUNT_COL)));
        contact.setFirstContact(new Date(resultSet.getDate(FIRST_CONTACT_COL).getTime()));
        contact.setLastContact(new Date(resultSet.getDate(LAST_CONTACT_COL).getTime()));
        List<String> sharedEventIds = arrayToStringList(resultSet.getArray(SHARED_EVENTS_COL));
        contact.setSharedEventIds(stringListToUUIDList(sharedEventIds));
        contact.setContactStatus(ContactStatus.values()[resultSet.getInt(STATUS_COL)]);
        return contact;
    }

}
