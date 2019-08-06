package me.arrush.javabase.statements;

import javafx.util.Pair;
import me.arrush.javabase.statements.clauses.IClause;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings({"WeakerAccess", "FieldCanBeLocal"})
public final class Statement {
    private StatementType type;
    private final List<IClause> clauses;
    private String DROP_DATABASE, CREATE_TABLE, DROP_TABLE, DELETE_ROW, SELECT, INSERT, UPDATE;

    Statement(StatementType type, List<IClause> clauses) {
        this.type = type;
        this.clauses = clauses;

        this.DROP_DATABASE =  "DROP DATABASE IF EXISTS %s";
        this.CREATE_TABLE =  "CREATE TABLE IF NOT EXISTS %s (%s)";
        this.DROP_TABLE =   "DROP TABLE IF EXISTS %s";
        this.INSERT =  "INSERT INTO %s";
        this.SELECT = "SELECT %s FROM %s";
        this.UPDATE =  "UPDATE %s SET %s WHERE %s";
        this.DELETE_ROW =  "DELETE FROM %s WHERE %s";
    }

    Statement(List<IClause> clauses) {
        this(null, clauses);
    }

    public String getStatement(StatementType type) {
        switch (type) {
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
        return this.getStatement(this.type);
    }

    public String toFullString() {
        return Stream.concat(this.clauses.stream().map(IClause::getPair), Stream.of(new Pair<>(0, this.getStatement(this.type))))
                .sorted(Comparator.comparingInt(Pair::getKey)).map(Pair::getValue).collect(Collectors.joining(" "));
    }
}
