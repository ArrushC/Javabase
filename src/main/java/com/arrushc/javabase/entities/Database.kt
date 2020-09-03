package com.arrushc.javabase.entities

import com.arrushc.javabase.entities.database.*
import com.arrushc.javabase.utils.DatabaseUtils
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException


@Suppress("MemberVisibilityCanBePrivate")
abstract class Database internal constructor(val name: String, val type: DatabaseType, className: String = "org.hsqldb.jdbcDriver", val dataSource: HikariDataSource): Entity() {
    val connection: Connection = this.dataSource.connection
    val tables: Set<Table>

    init {
        Class.forName(className)
        this.tables = DatabaseUtils.formTableObjects(this)
    }

    @Throws(SQLException::class)
    fun close() = this.connection.close()

    fun drop() {
        val deleteQuery: String = when (type) {
            DatabaseType.MARIADB, DatabaseType.MYSQL, DatabaseType.POSTGRESQL -> "DROP DATABASE IF EXISTS ${this.name}"
            DatabaseType.SQLITE -> "DETACH DATABASE ${this.name};"
        }
        this.makePreparedStatement(deleteQuery).executeUpdate()
        this.close()
    }

    @Throws(SQLException::class)  fun makePreparedStatement(sql: String?): PreparedStatement = connection.prepareStatement(sql)

    companion object {
        private fun formHikari(url: String): HikariDataSource {
            val ds = HikariDataSource()
            ds.jdbcUrl = url
            return ds
        }

        @Throws(SQLException::class) internal fun getMySQLDataSource(ip: String?, port: Int, dbName: String?): HikariDataSource = formHikari("jdbc:mysql://${ip}:${port}/${dbName}")
        @Throws(SQLException::class) internal fun getSQLiteDataSource(dbName: String?): HikariDataSource = formHikari("jdbc:sqlite:${dbName}")
        @Throws(SQLException::class) internal fun getMariaDataSource(hostName: String?, dbName: String?, username: String, password: String, usePipelineAuth: Boolean = false): HikariDataSource = formHikari("jdbc:mariadb://${hostName}/${dbName}${if (username.isNotEmpty()) "?user=${username}" else ""}${if (password.isNotEmpty()) "&?password=${username}" else ""}&?usePipelineAuth=${usePipelineAuth}")
        @Throws(SQLException::class) internal fun getPostgresDataSource(port: Int, dbName: String?, username: String, password: String, ssl: Boolean = false): HikariDataSource = formHikari("jdbc:postgresql://localhost:${port}/${dbName}?user=${username}&password=${password}&ssl=${ssl}")


        @Throws(SQLException::class, ClassNotFoundException::class)  fun withMaria(hostName: String, dbName: String, username: String, password: String, usePipelineAuth: Boolean = false): MariaDatabase = MariaDatabase(hostName, dbName, username, password, usePipelineAuth)
        @Throws(SQLException::class, ClassNotFoundException::class)  fun withMySQL(ip: String?, port: Int, dbName: String): MySQLDatabase = MySQLDatabase(ip, port, dbName)
        @Throws(SQLException::class, ClassNotFoundException::class)  fun withPostgres(port: Int, dbName: String, userId: String, pass: String, ssl: Boolean = false): PGDatabase = PGDatabase(port, dbName, userId, pass, ssl)
        @Throws(SQLException::class, ClassNotFoundException::class)  fun withSQLite(dbName: String): SQLiteDatabase = SQLiteDatabase(dbName)
    }
}
