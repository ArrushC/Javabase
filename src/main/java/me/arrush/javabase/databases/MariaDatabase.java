package me.arrush.javabase.databases;

import java.sql.SQLException;

@SuppressWarnings({"WeakerAccess", "unused"})
public final class MariaDatabase extends Database {
    MariaDatabase(String hostName, String dbName, boolean usePipelineAuth) throws ClassNotFoundException, SQLException {
        super(dbName, DatabaseType.MARIADB, "org.mariadb.jdbc.Driver", getMariaConnection(hostName, dbName, usePipelineAuth));
    }

    MariaDatabase(String hostName, String dbName) throws SQLException, ClassNotFoundException {
        this(hostName, dbName, false);
    }
}
