package com.arrushc.javabase.query

import com.arrushc.javabase.entities.Database
import com.arrushc.javabase.entities.Entity
import com.arrushc.javabase.entities.Table
import com.arrushc.javabase.statement.Statement
import java.sql.SQLException


abstract class Query<T : Entity> internal constructor(protected val database: Database,
                                                      protected val table: Table?=null,
                                                      protected val statement: Statement) {
    protected var hasQueried: Boolean = false

    protected abstract fun canQueue(): Boolean

    open fun queue(): QueryResult {
        require(!hasQueried) {"A query has already been performed!"}
        require(canQueue()) {"Cannot queue specific SQL commands"}
        try {
            database.connection.prepareStatement(this.statement.sqlStatement).use { statement ->
                statement.executeUpdate()
                statement.close()
            }
            this.hasQueried = true
            return QueryResult(state=QueryResult.QueryResultState.SUCCESSFUL)
        } catch (e: SQLException) {
            this.hasQueried = true
            @Suppress("UNREACHABLE_CODE")
            return if (e.message!!.contains("closed")) QueryResult(resultValue = e, state=QueryResult.QueryResultState.FAILURE_CLOSED, stateReason=e.message!!) else
                QueryResult(resultValue = e, state=QueryResult.QueryResultState.FAILURE_ERRONEOUS, stateReason=e.message!!)
        }
    }
}