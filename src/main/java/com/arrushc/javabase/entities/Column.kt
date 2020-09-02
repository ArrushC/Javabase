package com.arrushc.javabase.entities

import com.arrushc.javabase.datatype.Datatype


/**
 * Shares the same behaviour as a column in the database.
 *
 * This must be explicitly instantiated and kept so it can
 * be used for tables.
 *
 * @see Row
 * @see Table
 */
data class Column(val name: String, val dataType: Datatype) {


    override fun equals(other: Any?): Boolean = if (this.name == (other as Column).name) {
        require(this.dataType == other.dataType) {"Columns of same type must not have same name!"}
        true
    } else {
        false
    }

    override fun toString(): String = "${this.name} ${this.dataType}"

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + dataType.hashCode()
        return result
    }
}