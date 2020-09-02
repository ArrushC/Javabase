package com.arrushc.javabase.query

class QueryResult internal constructor(val resultValue: Any? = null, val stateReason: String = "No reason as the query was successful", val state: QueryResultState) {
    fun isSuccess() = this.state == QueryResultState.SUCCESSFUL

    enum class QueryResultState {
        SUCCESSFUL, FAILURE_ERRONEOUS, FAILURE_CLOSED
    }
}