package me.arrush.javabase;

import me.arrush.javabase.databases.Database;
import me.arrush.javabase.entities.Column;
import me.arrush.javabase.entities.Row;
import me.arrush.javabase.entities.Table;
import me.arrush.javabase.entities.Value;
import me.arrush.javabase.enums.DataType;
import me.arrush.javabase.statements.Statement;
import me.arrush.javabase.statements.StatementType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Sends an executive request to the database from the statement.
 *
 * Before implementing this, there would always be somewhat complications
 * regarding statement executions within sql libraries, or your own.
 * In a nutshell really, this just allows execution to be more flexible
 * and straightforward.
 *
 * @param <T> The subclass of the entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class Query<T extends DatabaseEntity> {
    private final Database database;
    private final Table table;
    private final T entity;
    private final StatementType type;
    private String statement;

    public Query(Database database, T entity, StatementType type, String statement) {
        this.database = database;
        this.table = null;
        this.entity = entity;
        this.type = type;
        this.statement = statement;
    }
    public Query(Database database, T entity, StatementType type) { this(database, entity, type, null); }
    public Query(Database database, StatementType type, String statement) { this(database, null, type, statement); }
    public Query(Database database, StatementType type) { this(database, null, type, null); }
    public Query(Table table, T entity, StatementType type, String statement) { this(table.getDatabase(), entity, type, statement); }
    public Query(Table table, T entity, StatementType type) { this(table, entity, type, null); }
    public Query(Table table, StatementType type, String statement) { this(table, null, type, statement); }
    public Query(Table table, StatementType type) { this(table, null, type); }

    public Query<T> withStatement(Statement statement) {
        if (!statement.getType().equals(this.type)) throw new IllegalArgumentException("Statement Type must be the same!");
        this.statement = statement.toString();
        return this;
    }

    public Query<T> withStatement(String statement) {
        this.statement = statement;
        return this;
    }

    public Query<T> appendStatement(String statement) {
        this.statement += statement;
        return this;
    }

    public Query<T> modifyStatement(Function<String, String> function) {
        this.statement = function.apply(this.statement);
        return this;
    }

    public void queue(Consumer<T> success, Consumer<Throwable> error) {
        try (PreparedStatement statement = this.database.getConnection().prepareStatement(this.statement)) {
            statement.executeUpdate();
            statement.close();
            if (success != null && this.entity != null) success.accept(this.entity);
        } catch (SQLException e) {
            if (error != null) error.accept(e);
             else e.printStackTrace();
        }
    }
    public void queue(Consumer<T> success) { this.queue(success, null);}
    public void queue() {this.queue(null, null);}

    public T doAndReturn(Consumer<T> success, Consumer<Throwable> error) {
        this.queue(success, error);
        return this.entity;
    }
    public T doAndReturn(Consumer<T> success) {return this.doAndReturn(success, null);}
    public T doAndReturn() {return this.doAndReturn(null, null);}

    public ResultSet fetchResult(Consumer<Throwable> error) {
        try (PreparedStatement statement = this.database.makePreparedStatement(this.statement)) {
            ResultSet rs = statement.executeQuery();
            statement.close();
            return rs;
        } catch (SQLException e) {
            if (error != null) error.accept(e); else e.printStackTrace();
            return null;
        }
    }

    public ResultSet fetchResult() {return this.fetchResult(null);}

    public Row fetch(Consumer<Throwable> error) {
        Row r = this.entity == null ? new Row(this.table) : (Row) this.entity;
        try {
            return r.modify(this.parseRow(Objects.requireNonNull(this.fetchResult(error))));
        } catch (SQLException e) {
            if (error != null) error.accept(e); else e.printStackTrace();
            return null;
        }
    }
    public Row fetch() {return this.fetch(null);}
    public List<Row> fetchMultiple(Consumer<Throwable> error) {
        try {
            ResultSet rs = this.fetchResult(error);
            List<Row> rows = new ArrayList<>();
            while (rs != null && rs.next()) {
                rows.add(new Row((Table) this.entity, this.parseRow(rs)));
            }
            return rows;
        } catch (SQLException e) {
            if (error != null) error.accept(e); else e.printStackTrace();
            return null;
        }
    }
    public List<Row> fetchMultiple() {return this.fetchMultiple(null);}

    private List<Value<?>> parseRow(ResultSet rs) throws SQLException {
        List<Value<?>> values = new ArrayList<>();
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            Object o = rs.getObject(i);
            values.add(new Value<>(o, new Column(rs.getMetaData().getColumnName(i), DataType.from(o)) ));
        }
        return values;
    }
}
