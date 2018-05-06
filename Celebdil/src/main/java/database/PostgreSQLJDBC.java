package main.java.database;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;

@Repository
public class PostgreSQLJDBC implements Database {

    public Connection getConnection() {

        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            //TODO: Create config locally for database, user, and password
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/durinsfolktest",
                            "postgres", "AsLk2#mN");
        } catch (Exception e) {
            
        }
        return c;
    }
}