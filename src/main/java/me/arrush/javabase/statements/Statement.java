package me.arrush.javabase.statements;

@SuppressWarnings({"WeakerAccess", "FieldCanBeLocal"})
public final class Statement {
    private final StatementType type;
    private String DROP_DATABASE, CREATE_TABLE, DROP_TABLE, DELETE_ROW, SELECT, INSERT, UPDATE;

    Statement(StatementType type) {
        this.type = type;

        this.DROP_DATABASE =  "DROP DATABASE IF EXISTS %s";
        this.CREATE_TABLE =  "CREATE TABLE IF NOT EXISTS %s (%s)";
        this.DROP_TABLE =   "DROP TABLE IF EXISTS %s";
        this.INSERT =  "INSERT INTO %s";
        this.SELECT = "SELECT %s FROM %s";
        this.UPDATE =  "UPDATE %s SET %s WHERE %s";
        this.DELETE_ROW =  "DELETE FROM %s WHERE %s";
    }

    public String getStatement() {
        switch (this.type) {
            case INSERT: return this.INSERT;
            case SELECT: return this.SELECT;
            case UPDATE: return this.UPDATE;
            case DELETE_ROW: return this.DELETE_ROW;
            case DROP_TABLE: return this.DROP_TABLE;
            case CREATE_TABLE: return this.CREATE_TABLE;
            case DROP_DATABSE: return this.DROP_DATABASE;
        }
        return null;
    }

    public StatementType getType() { return this.type; }

    @Override
    public String toString() {
        return this.getStatement();
    }
}
