package me.arrush.javabase.databases;

import com.zaxxer.hikari.HikariDataSource;
import me.arrush.javabase.DatabaseEntity;
import me.arrush.javabase.Query;
import me.arrush.javabase.statements.StatementType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("WeakerAccess")
public abstract class Database extends DatabaseEntity {
    protected final Connection connection;
    protected final String name;
    protected final DatabaseType type;

    Database(String name, DatabaseType type, String className, Connection connection) throws ClassNotFoundException {
        this.name = name;
        this.type = type;
        Class.forName(className);
        this.connection = connection;
    }

    private static HikariDataSource formHikari(String url) {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(url);
        return ds;
    }

    static Connection getMySQLConnection(String ip, int port, String dbName) throws SQLException {
        return formHikari(String.format("jdbc:mysql://%s:%d/%s", ip, port, dbName)).getConnection();
    }

    static Connection getSQLiteConnection(String dbName) throws SQLException {
        return formHikari(String.format("jdbc:sqlite:%s", dbName)).getConnection();
    }

    static Connection getPostgresConnection(int port, String dbName, String username, String pass) throws SQLException {
        HikariDataSource ds = formHikari(String.format("jdbc:postgresql://localhost:%s/%s", port, dbName));
        ds.setUsername(username);
        ds.setPassword(pass);
        return ds.getConnection();
    }

    static Connection getMariaConnection(String hostName, String dbName, boolean usePipelineAuth) throws SQLException {
        return formHikari(String.format("jdbc:mariadb://%s/%s?usePipelineAuth=false%b", hostName, dbName, usePipelineAuth)).getConnection();
    }


    public static MariaDatabase withMaria(String hostName, String dbName, boolean usePipelineAuth) throws SQLException, ClassNotFoundException { return new MariaDatabase(hostName, dbName, usePipelineAuth); }
    public static MariaDatabase withMaria(String hostName, String dbName) throws SQLException, ClassNotFoundException { return withMaria(hostName, dbName, false); }
    public static MySQLDatabase withMySQL(String ip, int port, String dbName) throws SQLException, ClassNotFoundException { return new MySQLDatabase(ip, port, dbName); }
    public static PGDatabase withPostgres(int port, String dbName, String userId, String pass) throws SQLException, ClassNotFoundException { return new PGDatabase(port, dbName, userId, pass); }
    public static SQLiteDatabase withSQLite(String dbName) throws SQLException, ClassNotFoundException { return new SQLiteDatabase(dbName);}

    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void drop() {
        String deleteQuery = "";

        switch (this.type) {
            case MARIADB:
            case MYSQL:
            case POSTGRESQL:
                deleteQuery = "DROP DATABASE IF EXISTS %s";
                break;
            case SQLITE:
                deleteQuery = "DETACH DATABASE %s;";
                break;
        }
        deleteQuery = String.format(deleteQuery, this.name);
        new Query<>(this, this, StatementType.DROP_DATABSE, deleteQuery).queue();
        this.close();
    }

    public String getName() { return this.name; }
    public DatabaseType getType() { return this.type; }
    public Connection getConnection() {
        return this.connection;
    }
    public Statement makeStatement() throws SQLException {return this.connection.createStatement();}
    public PreparedStatement makePreparedStatement(String sql) throws SQLException { return this.connection.prepareStatement(sql);}
}
