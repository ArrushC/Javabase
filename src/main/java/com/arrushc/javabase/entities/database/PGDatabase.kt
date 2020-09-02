package com.arrushc.javabase.entities.database

import com.arrushc.javabase.entities.Database

class PGDatabase internal constructor(port: Int, dbName: String, username: String, pass: String, ssl: Boolean = false) : Database(dbName, DatabaseType.POSTGRESQL, "org.postgresql.Driver", getPostgresDataSource(port, dbName, username, pass, ssl))