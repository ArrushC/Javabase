package me.arrush.javabase.databases;

import java.sql.SQLException;

@SuppressWarnings("WeakerAccess")
public final class PGDatabase extends Database {

    PGDatabase(int port, String dbName, String username, String pass) throws ClassNotFoundException, SQLException {
        super(dbName, DatabaseType.POSTGRESQL, "org.postgresql.Driver", Database.getPostgresConnection(port, dbName, username, pass));
    }
}
