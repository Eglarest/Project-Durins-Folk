package main.java.database;

import java.sql.Connection;

public interface Database {

    Connection getConnection();
}
