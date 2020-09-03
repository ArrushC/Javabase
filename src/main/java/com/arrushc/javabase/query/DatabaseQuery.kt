package com.arrushc.javabase.query

import com.arrushc.javabase.entities.Database
import com.arrushc.javabase.statement.Statement

class DatabaseQuery(database: Database,
                    statement: Statement): Query<Database>(database, null, statement) {
    override fun canQueue(): Boolean = this.statement.type in listOf(
            Statement.StatementType.DROP_DATABSE,
            Statement.StatementType.CREATE_TABLE,
            Statement.StatementType.DROP_TABLE,
    )

}