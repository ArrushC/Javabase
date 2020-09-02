package com.arrushc.javabase.entities


class Table(val database: Database, val name: String, val columns: Set<Column>) : Entity() {


}