package com.arrushc.javabase.entities.database

import com.arrushc.javabase.entities.Database

class MariaDatabase internal constructor(hostName: String?, dbName: String?, username: String, password: String, usePipelineAuth: Boolean = false) : Database(dbName!!, DatabaseType.MARIADB, "org.mariadb.jdbc.Driver", getMariaDataSource(hostName, dbName, username, password, usePipelineAuth))