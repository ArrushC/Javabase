package com.arrushc.javabase.entities.database

import com.arrushc.javabase.entities.Database

class SQLiteDatabase internal constructor(dbName: String = ":memory") : Database(dbName, DatabaseType.SQLITE, "org.sqlite.JDBC", getSQLiteDataSource(dbName))