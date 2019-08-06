package me.arrush.javabase.statements.clauses;

import javafx.util.Pair;
import me.arrush.javabase.databases.DatabaseType;
import me.arrush.javabase.statements.StatementType;

@SuppressWarnings("unused")
public enum  PGClauses implements IClause {
    // Common clauses
    QUERY(-1, "WITH %s\n", StatementType.INSERT, StatementType.UPDATE, StatementType.SELECT),
    QUERY_RECURSIVE(-1, "WITH RECURSIVE %s\n", StatementType.INSERT, StatementType.UPDATE, StatementType.SELECT),

    // Insert statement
    INSERT_COLUMNS(1, "(%s)",  StatementType.INSERT),
    INSERT_VALUES(2, "VALUES (%s)",  StatementType.INSERT),
    INSERT_VALUES_DEFAULT(2, "DEFAULT VALUES",  StatementType.INSERT),
    INSERT_VALUES_MULTIPLE(2, "VALUES %s",  StatementType.INSERT), // Multiple values i.e. () () (). Must convert into an array when formatting.
    INSERT_VALUES_QUERY(2, "%s",  StatementType.INSERT),
    INSERT_RETURNING_ALL(3, "RETURNING *", StatementType.INSERT),
    INSERT_RETURNING_EXP(3, "RETURNING %s", StatementType.INSERT),
    INSERT_RETURNING_EXP_AS(3, "RETURNING %s AS %s", StatementType.INSERT),

    // Select statement
    SELECT_ALL(1, "ALL", StatementType.SELECT),
    SELECT_DISTINCT(1, "DISTINCT %s", StatementType.SELECT),
    SELECT_DISTINCT_ON(1, "DISTINCT ON (%s) %s"),
    SELECT_FIELDS_ALL(2, "*", false, false, StatementType.SELECT),
    SELECT_FIELDS_CERTAIN(2, "%s", false, false, StatementType.SELECT),
    SELECT_FROM_MULTIPLE(3, "FROM %s", StatementType.SELECT),
    SELECT_FROM_TABLE(3, "FROM ", StatementType.SELECT), // Finish the select from part.
    // where from_item can be one of:
    //
    //    [ ONLY ] table_name [ * ] [ [ AS ] alias [ ( column_alias [, ...] ) ] ]
    //    ( select ) [ AS ] alias [ ( column_alias [, ...] ) ]
    //    with_query_name [ [ AS ] alias [ ( column_alias [, ...] ) ] ]
    //    function_name ( [ argument [, ...] ] ) [ AS ] alias [ ( column_alias [, ...] | column_definition [, ...] ) ]
    //    function_name ( [ argument [, ...] ] ) AS ( column_definition [, ...] )
    //    from_item [ NATURAL ] join_type from_item [ ON join_condition | USING ( join_column [, ...] ) ]
    SELECT_WHERE(4, "WHERE %s", StatementType.SELECT),
    SELECT_GROUPBY(5, "GROUP BY %s", StatementType.SELECT),
    SELECT_HAVING(6, "HAVING %s", StatementType.SELECT),
    SELECT_WINDOW(7, "WINDOW %s AS (%s)", StatementType.SELECT),
    SELECT_WINDOW_MULTIPLE(7, "WINDOW %s AS %s", StatementType.SELECT),
    // [ { UNION | INTERSECT | EXCEPT } [ ALL | DISTINCT ] select ] Position 8
    SELECT_ORDERBY_ASC(9, "ORDER BY %s ASC")



    // Update statement
    // Delete row statement
    // Delete table statement
    // Create table statement

    ;
    private final int position;
    private final String clause;
    private final boolean isOptional;
    private final boolean canJoin;
    private final boolean isJoined;
    private final StatementType[] type;

    PGClauses(int position, String clause, boolean canJoin, boolean isOptional, boolean isJoined,StatementType... type) {
        this.position = position;
        this.clause = clause;
        this.canJoin = canJoin;
        this.isOptional = isOptional;
        this.isJoined = isJoined;
        this.type = type;
    }
    PGClauses(int position, String clause, boolean canJoin, boolean isOptional, StatementType... type) { this(position, clause, canJoin, isOptional, true, type); }
    PGClauses(int position, String clause, boolean canJoin, StatementType... type) { this(position, clause, canJoin, true, type);}
    PGClauses(int position, String clause, StatementType... type) { this(position, clause, true, type); }

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
