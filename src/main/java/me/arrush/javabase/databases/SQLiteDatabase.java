package me.arrush.javabase.databases;

import java.sql.SQLException;

@SuppressWarnings("WeakerAccess")
public final class SQLiteDatabase extends Database {
    SQLiteDatabase(String dbName) throws ClassNotFoundException, SQLException {
        super(dbName, DatabaseType.SQLITE, "org.sqlite.JDBC", getSQLiteConnection(dbName));
    }

    SQLiteDatabase() throws SQLException, ClassNotFoundException {
        this(":memory");
    }
}
