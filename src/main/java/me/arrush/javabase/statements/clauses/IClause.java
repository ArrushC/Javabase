package me.arrush.javabase.statements.clauses;

import me.arrush.javabase.databases.DatabaseType;
import me.arrush.javabase.statements.StatementType;
import javafx.util.Pair;
;

public interface IClause {
    String getClause();
    int getPosition();
    Pair<Integer, String> getPair();
    DatabaseType getDatabaseType();
    boolean canJoin();
    boolean isOptional();
    boolean isJoined();
    StatementType[] getTypes();
}
