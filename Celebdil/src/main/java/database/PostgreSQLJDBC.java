package main.java.database;

import main.java.exception.InternalFailureException;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;

@Repository
public class PostgreSQLJDBC implements Database {

    private int waitCount = 250;

    public Connection getConnection() throws InternalFailureException {

        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            //TODO: Create config locally for database, user, and password
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/durinsfolk",
                            "postgres", "AsLk2#mN");
        } catch (Exception e) {
            if(waitCount < 4000) {
                waitCount = waitCount * 2;
                try {
                    wait(waitCount);
                    getConnection();
                } catch (InterruptedException error) {
                    throw new InternalFailureException("Connection with database interrupted");
                }
            } else {
                throw new InternalFailureException("Unable to connect to database");
            }
        }
        return connection;
    }
}