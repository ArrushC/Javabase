package com.arrushc.javabase.datatype

enum class DatatypeGroup(val clazz: Class<*>? = null) {
    BOOLEAN(Boolean::class.java),
    NUMERIC(Int::class.java),
    CHAR(Char::class.java),
    STRING(String::class.java),
    DECIMAL(Float::class.java),
    TEMPORAL,
    OTHER;

}