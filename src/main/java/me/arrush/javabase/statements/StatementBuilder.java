package me.arrush.javabase.statements;

import me.arrush.javabase.statements.clauses.IClause;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class StatementBuilder {
    private final List<IClause> clauses; // clauses...
    private StatementType type;

    public StatementBuilder(StatementType type) {
        this.type = type;
        this.clauses = new ArrayList<>();
    }


    public StatementBuilder withStatement(StatementType type) {
        this.type = type;
        return this;
    }

    public StatementBuilder addClauses(IClause... clauses) {
        Collections.addAll(this.clauses, clauses);
        return this;
    }

    public StatementBuilder addClause(IClause clause) {
        this.clauses.add(clause);
        return this;
    }

    public Statement build() {
        return new Statement(this.type, this.clauses);
    }
}
