package main.java.database;

import main.java.exception.InternalFailureException;

import java.sql.Connection;

public interface Database {

    Connection getConnection() throws InternalFailureException;
}
