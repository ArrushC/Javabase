package me.arrush.javabase.statements.clauses;

import javafx.util.Pair;
import me.arrush.javabase.databases.DatabaseType;
import me.arrush.javabase.statements.StatementType;

public enum MySQLClauses implements IClause{

    ;
    private final int position;
    private final String clause;
    private final boolean isOptional;
    private final boolean canJoin;
    private final boolean isJoined;
    private final StatementType[] type;

    MySQLClauses(int position, String clause, boolean canJoin, boolean isOptional, boolean isJoined,StatementType... type) {
        this.position = position;
        this.clause = clause;
        this.canJoin = canJoin;
        this.isOptional = isOptional;
        this.isJoined = isJoined;
        this.type = type;
    }
    MySQLClauses(int position, String clause, boolean canJoin, boolean isOptional, StatementType... type) { this(position, clause, canJoin, isOptional, true, type); }
    MySQLClauses(int position, String clause, boolean canJoin, StatementType... type) { this(position, clause, canJoin, true, type);}
    MySQLClauses(int position, String clause, StatementType... type) { this(position, clause, true, type); }

    @Override
    public int getPosition() { return this.position; }

    @Override
    public Pair<Integer, String> getPair() {
        return new Pair<>(this.position, this.clause);
    }

    @Override
    public String getClause() { return this.clause; }

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.POSTGRESQL;
    }

    @Override
    public boolean canJoin() { return this.canJoin;}

    @Override
    public boolean isOptional() { return this.isOptional; }

    @Override
    public boolean isJoined() { return this.isJoined; }

    @Override
    public StatementType[] getTypes() { return this.type; }

}
