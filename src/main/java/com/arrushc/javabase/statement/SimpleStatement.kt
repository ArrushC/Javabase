package com.arrushc.javabase.statement

import com.arrushc.javabase.utils.copy

enum class SimpleStatement(private val statement: String) {
    // Postgres specific statements goes down below.
    // Maria specific statements goes down below.
    // SQLite specific statements goes down below.
    SQLITE_DROP_DATABASE("DROP DATABASE IF EXISTS %s"),
    SQLITE_DROP_ROW("DELETE FROM %s WHERE %s"),
    // MySQL specific statements goes down below.
    // Statements that are applicable in all databases goes down below.
    CREATE_TABLE_NOTEXISTS("CREATE TABLE IF NOT EXISTS %s (%s)"),
    CREATE_TABLE("CREATE TABLE %s (%s)"),
    DROP_TABLE("DROP TABLE IF EXISTS %s"),
    INSERT_ROW("INSERT INTO %s"),
    SELECT_FIELDS("SELECT %s FROM %s WHERE %s"),
    UPDATE_FIELDS("UPDATE %s SET %s WHERE %s");

    fun format(vararg args: Any?): String  = this.statement.copy().format(*args)
}