package me.arrush.javabase.entities;

import me.arrush.javabase.DatabaseEntity;
import me.arrush.javabase.Query;
import me.arrush.javabase.databases.Database;
import me.arrush.javabase.statements.SimpleStatements;
import me.arrush.javabase.statements.StatementType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
public class Table extends DatabaseEntity {
    protected final Database database;
    protected final String name;
    protected final List<Column> columns;

    /**
     * Instantiates the {@link Table Table} class.
     *
     * Note this is a package-private constructor. We should only
     * allow {@link TableCreator} to make tables through the
     * {@link TableCreator#build(Database)})}.
     *
     * @param database The database that handles the connection.
     *                 It is used to form queries from statements.
     * @param name The name of the table.
     * @param columns The provided columns of the table, reflected
     *                in the actual table.
     */
    Table(Database database, String name, List<Column> columns) {
        this.database = database;
        this.name = name;
        this.columns = columns;
    }

    public String getName() { return this.name; }
    public Database getDatabase() { return this.database; }
    public List<Column> getColumns() { return this.columns; }

    public Query<Table> create() {
        return this.openTableQuery(StatementType.CREATE_TABLE)
                .withStatement(SimpleStatements.CREATE_TABLE.format(this.name, this.columns.stream().map(Column::toString).collect(Collectors.joining(" "))));
    }
    public Query<Table> drop() {
        return this.openTableQuery(StatementType.DROP_TABLE)
                .withStatement(SimpleStatements.DROP_TABLE.format(this.name));
    }

    public Query<Row> insert() {return this.insert(null);}
    public Query<Row> insert(Row row) {return this.openRowQuery(StatementType.INSERT, row);}
    public Query<Row> dropRow() {return this.dropRow(null);}
    public Query<Row> dropRow(Row row) {return this.openRowQuery(StatementType.DELETE_ROW, row);}
    public Query<Row> select() {return this.openRowQuery(StatementType.SELECT);}
    public Query<Row> update() {return this.update(null);}
    public Query<Row> update(Row row) {return this.openRowQuery(StatementType.UPDATE, row);}

    public Query<Table> openTableQuery(StatementType type) {return new Query<>(this.database, this, type);}
    public Query<Row> openRowQuery(StatementType type, Row row) {return new Query<>(this.database, row, type);}
    public Query<Row> openRowQuery(StatementType type) {return this.openRowQuery(type, null);}

    public static class TableCreator {
        private final String name;
        private final List<Column> columns;

        public TableCreator(String name) {
            this.name = name;
            this.columns = new ArrayList<>();
        }

        public TableCreator addColumn(Column column) {
            this.columns.add(column);
            return this;
        }

        public TableCreator addColumns(Column... columns) {
            Collections.addAll(this.columns, columns);
            return this;
        }

        public TableCreator addColumns(Collection<Column> columns) {
            this.columns.addAll(columns);
            return this;
        }

        public Table build(Database database) {
            return new Table(database, this.name, this.columns);
        }

        public Table buildAndCreate(Database database) {
            return new Table(database, this.name, this.columns)
                    .create().doAndReturn();
        }
    }
}
