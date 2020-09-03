package com.arrushc.javabase.query

import com.arrushc.javabase.entities.Table
import com.arrushc.javabase.statement.Statement

class TableQuery(table: Table, statement: Statement): Query<Table>(table.database, table, statement) {
    override fun canQueue(): Boolean = this.statement.type in listOf(
            Statement.StatementType.CREATE_TABLE,
            Statement.StatementType.DROP_TABLE,
    )

}