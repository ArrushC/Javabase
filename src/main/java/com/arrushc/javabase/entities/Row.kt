package com.arrushc.javabase.entities

import com.arrushc.javabase.datatype.Datatype
import java.sql.ResultSet


@Suppress("MemberVisibilityCanBePrivate")
data class Row(val table: Table,
               val cells: Set<Cell<*>> = mutableSetOf(),
               private val canModify: Boolean = true,
               private val canInsert: Boolean = true) : Entity(modifiable=canModify, insertable=canInsert) {

    /**
     * Inserts the data of this row into the database,
     * provided that this is instantiated manually.
     *
     * @return The [Row] instance.
     */
    fun insert(): Row? {
        if (!this.insertable) { throw IllegalAccessException("This row is meant to be modifiable and readable only!") }
        val cells = cells.joinToString(",") { it.getValueDisplay() }

        //return table.insert(this).withStatement(String.format("INSERT INTO %s VALUES (%s)", table.name, cells)).doAndReturn()
        return null
    }

    fun cell(column: String?): Cell<*> = this.cells.stream().filter { it.column.name == column }.findFirst().orElse(null)
    fun getObject(column: String?): Any? = this.cell(column).value
    fun string(column: String?): String =  getObject(column) as String
    fun integer(column: String?): Int = getObject(column) as Int
    fun long(column: String?): Long = getObject(column) as Long
    fun boolean(column: String?): Boolean = getObject(column) as Boolean

    companion object {
        fun fromQuery(table: Table, rs: ResultSet): Row {
            val cells: MutableSet<Cell<*>> = mutableSetOf()
            for (i in 1..rs.metaData.columnCount) {
                val o = rs.getObject(i)
                cells.add(Cell(o, Column(rs.metaData.getColumnName(i), Datatype.from(o)!!)))
            }
            return Row(table, cells)
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    data class Cell<T>(var value: T, var column: Column) {
        override fun toString(): String = "${this.column.name} = ${this.getValueDisplay()}"
        fun asConditionString(): String = "${this.column.name} == ${this.getValueDisplay()}"
        fun getValueDisplay(): String = if (value is String || value is List<*>) "\'${this.value.toString()}\'" else value.toString()
    }
}