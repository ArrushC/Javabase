package me.arrush.example;

import me.arrush.javabase.databases.Database;
import me.arrush.javabase.entities.Column;
import me.arrush.javabase.entities.Row;
import me.arrush.javabase.entities.Table;
import me.arrush.javabase.entities.Value;
import me.arrush.javabase.enums.DataType;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Example {

    @SuppressWarnings({"RedundantThrows", "RedundantSuppression"})
    public static void main(String[] args) throws Throwable {
        makeDatabase();
    }

    private static void makeDatabase() throws SQLException, ClassNotFoundException {
        // Connect to our database with the provided: port, dbName, userId and pass
        Database db = Database.withPostgres(5432, "postgres", "Arrush", "Test6");
        // Make a table instance and reflect it in the database.
        Table table = new Table.TableCreator("People").addColumns(getColumns()).build(db).create().doAndReturn();
        // Make new rows and insert them.
        makeRow(table,"Arrush", 16).insert();
        makeRow(table, "Jacob", 21).insert();
        // Selecting rows.
        List<Row> rows = table.select().withStatement("SELECT * FROM People").fetchMultiple();
        // Looping through rows to print out the values as confirmation of their presence.
        for (Row r: rows) {
            System.out.println( r.getString("name") );
        }
    }

    private static Row makeRow(Table table, String name, int age) {
        return new Row(table, Arrays.asList(new Value<>(name, getColumns().get(0)), new Value<>(age, getColumns().get(1))));
    }

    private static List<Column> getColumns() {
        return Arrays.asList(
                // First column: Name. Has maximum 30 characters.
                new Column("name", DataType.VARCHAR).modifyDataType(s -> String.format(s, "30")),
                // Second column: Age. It is an integer.
                new Column("age", DataType.INTEGER)
        );
    }
}
