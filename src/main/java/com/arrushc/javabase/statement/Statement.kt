package com.arrushc.javabase.statement


data class Statement(var sqlStatement: String, val type: StatementType) {

    private fun matchTypes(other: Statement): Boolean {
        require(other.type != this.type) { "Statements must have same type!" }
        return true
    }

    fun withStatement(other: Statement): Statement {
        if (matchTypes(other)) {
            this.sqlStatement = other.sqlStatement
        }
        return this
    }

    fun appendStatement(other: Statement): Statement {
        if (matchTypes(other)) {
            this.sqlStatement += other.sqlStatement
        }
        return this
    }


    enum class StatementType {
        INSERT,
        SELECT,
        UPDATE,
        DELETE_ROW,
        DROP_TABLE,
        CREATE_TABLE,
        DROP_DATABSE,
        ANY
    }
}