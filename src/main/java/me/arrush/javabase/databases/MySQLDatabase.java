package me.arrush.javabase.databases;

import java.sql.SQLException;

@SuppressWarnings("WeakerAccess")
public final class MySQLDatabase extends Database {
    MySQLDatabase(String ip, int port, String dbName) throws ClassNotFoundException, SQLException {
        super(dbName, DatabaseType.MYSQL, "com.mysql.jdbc.Driver", getMySQLConnection(ip, port, dbName));
    }
}
