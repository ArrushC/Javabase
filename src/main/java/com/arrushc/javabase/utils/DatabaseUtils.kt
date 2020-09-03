package com.arrushc.javabase.utils

import com.arrushc.javabase.datatype.Datatype
import com.arrushc.javabase.entities.Column
import com.arrushc.javabase.entities.Database
import com.arrushc.javabase.entities.Table
import org.apache.ddlutils.Platform
import org.apache.ddlutils.PlatformFactory

object DatabaseUtils {

    fun formTableObjects(database: Database): MutableSet<Table> {
        val platform: Platform = PlatformFactory.createNewPlatformInstance(database.dataSource)
        return platform.readModelFromDatabase("model").tables.map { formTableObject(database, it.name, platform)}.toMutableSet()
    }

    fun formTableObject(database: Database, tableName: String, platform: Platform?=null): Table {
        val platform: Platform = platform ?: PlatformFactory.createNewPlatformInstance(database.dataSource)
        val table = platform.readModelFromDatabase("model").tables.filter { it.name == tableName }[0]
        val columns: MutableSet<Column> = mutableSetOf()
        for (column in table.columns) {
            columns.add(Column(column.name, Datatype.from(column.type)!!))
        }
        return Table(database, table.name, columns)
    }

    fun formRowObjects() {

    }
}