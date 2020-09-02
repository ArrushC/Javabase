package com.arrushc.javabase.entities.database

import com.arrushc.javabase.entities.Database

class MySQLDatabase internal constructor(ip: String?, port: Int, dbName: String?) : Database(dbName!!, DatabaseType.MYSQL, "com.mysql.jdbc.Driver", getMySQLDataSource(ip, port, dbName))