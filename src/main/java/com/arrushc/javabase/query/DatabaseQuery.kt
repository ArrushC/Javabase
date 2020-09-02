package com.arrushc.javabase.query

import com.arrushc.javabase.entities.Database
import com.arrushc.javabase.statement.Statement

class DatabaseQuery(database: Database,
                    statement: Statement): Query<Database>(database, null, statement) {
    override fun canQueue(): Boolean {
        TODO("Not yet implemented")
    }

}