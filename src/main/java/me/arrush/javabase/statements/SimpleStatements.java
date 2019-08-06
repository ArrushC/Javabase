package me.arrush.javabase.statements;

public enum SimpleStatements {
    CREATE_TABLE("CREATE TABLE IF NOT EXISTS %s (%s)"),
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
