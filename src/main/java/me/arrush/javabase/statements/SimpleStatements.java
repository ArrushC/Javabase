package me.arrush.javabase.statements;

public enum SimpleStatements {
    // Postgres specific statements goes down below.
    // Maria specific statements goes down below.
    // SQLite specific statements goes down below.
    // MySQL specific statements goes down below.

    // Statements that are applicable in all databases goes down below.
    CREATE_TABLE_NOTEXISTS("CREATE TABLE IF NOT EXISTS %s (%s)"),
    CREATE_TABLE("CREATE TABLE %s (%s)"),
    DROP_TABLE("DROP TABLE IF EXISTS %s"),
    SELECT_FIELDS("SELECT %s FROM %s WHERE %s");

    private final String statement;

    SimpleStatements(String statement) {
        this.statement = statement;
    }


    public String format(Object... args) {
        return String.format(this.statement, args);
    }
}
