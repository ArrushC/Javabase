package com.arrushc.example;

import com.arrushc.javabase.entities.Database;

import java.sql.SQLException;

public class Example {

    @SuppressWarnings({"RedundantThrows", "RedundantSuppression"})
    public static void main(String[] args) throws Throwable {
        makeDatabase();
    }

    private static void makeDatabase() throws SQLException, ClassNotFoundException {
        // Connect to our database with the provided: port, dbName, userId and pass
        Database db = Database.Companion.withSQLite("TestDatabase");
        // Make a table instance and reflect it in the database.
        // Make new rows and insert them.
        // Selecting rows.
        // Looping through rows to print out the values as confirmation of their presence.
    }
}
