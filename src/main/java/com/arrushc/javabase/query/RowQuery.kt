package com.arrushc.javabase.query

import com.arrushc.javabase.datatype.Datatype
import com.arrushc.javabase.entities.Column
import com.arrushc.javabase.entities.Row
import com.arrushc.javabase.entities.Table
import com.arrushc.javabase.statement.Statement
import com.arrushc.javabase.statement.Statement.StatementType
import java.sql.ResultSet
import java.sql.SQLException
import java.util.function.Consumer


class RowQuery(table: Table, statement: Statement): Query<Row>(table.database, table, statement) {

    override fun canQueue(): Boolean = this.statement.type in listOf(
            StatementType.DELETE_ROW,
            StatementType.INSERT)

    fun fetchResultSet(error: Consumer<Throwable?>? = null): ResultSet? {
        require(!this.hasQueried) {"A query has already been performed!"}
        require(this.statement.type == StatementType.SELECT) {"Invalid SQL query - query statement must be 'select' command!"}
        try {
            this.database.makePreparedStatement(this.statement.sqlStatement).use { statement ->
                val rs = statement.executeQuery()
                statement.close()
                this.hasQueried = true
                return rs
            }
        } catch (e: SQLException) {
            error?.accept(e) ?: e.printStackTrace()
            this.hasQueried = true
            return null
        }
    }

    fun fetchRow(error: Consumer<Throwable?>?= null): Row? = parseRow(this.fetchResultSet(error)!!)


    fun fetchMultiple(error: Consumer<Throwable?>? = null): List<Row?>? { // Must be in RowQueryFlood
        try {
            database.makePreparedStatement(this.statement.sqlStatement).use { statement ->
                val rs = statement.executeQuery()
                val rows: MutableList<Row?> = ArrayList()
                while (rs.next()) {
                    rows.add(parseRow(rs))
                }
                statement.close()
                return rows
            }
        } catch (e: SQLException) {
            error?.accept(e) ?: e.printStackTrace()
            return null
        }
    }

    @Throws(SQLException::class)
    fun parseRow(rs: ResultSet): Row? {
        val cells: MutableSet<Row.Cell<*>> = mutableSetOf()
        for (i in 1..rs.metaData.columnCount) {
            val o = rs.getObject(i)
            cells.add(Row.Cell(o, Column(rs.metaData.getColumnName(i), Datatype.from(o)!!)))
        }
        return Row(this.table!!, cells)
    }
}