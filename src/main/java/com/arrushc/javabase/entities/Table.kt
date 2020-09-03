package com.arrushc.javabase.entities


data class Table(val database: Database, val name: String, val columns: Set<Column>, val rows: MutableSet<Row> =mutableSetOf()) : Entity() {

}