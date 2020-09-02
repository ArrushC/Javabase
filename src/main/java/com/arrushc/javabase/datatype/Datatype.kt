package com.arrushc.javabase.datatype

enum class Datatype(val type: String, val collection: DatatypeGroup) {
    // Boolean data type
    BOOLEAN("boolean", DatatypeGroup.BOOLEAN),
    // Character data types. VARCHAR(n) where n is the amount of characters.
    CHAR("CHAR(%s)", DatatypeGroup.CHAR), VARCHAR("VARCHAR(%s)", DatatypeGroup.STRING), TEXT("TEXT", DatatypeGroup.STRING),
    // Numeric data types
    SMALLINT("SMALLINT", DatatypeGroup.NUMERIC), INTEGER("INT", DatatypeGroup.NUMERIC), SERIAL("SERIAL", DatatypeGroup.NUMERIC), BIGINT("BIGINT", DatatypeGroup.NUMERIC),
    // Floating-point Number data types
    FLOAT("FLOAT(%s)", DatatypeGroup.DECIMAL), REAL("REAL", DatatypeGroup.DECIMAL), FLOAT8("FLOAT8", DatatypeGroup.DECIMAL), NUMERIC("NUMERIC(%s, %s)", DatatypeGroup.DECIMAL),  // (p, s) = real number with p digits with s number after the decimal point. Being the exact number

    // Temporal data types
    DATE("DATE", DatatypeGroup.TEMPORAL), TIME("TIME", DatatypeGroup.TEMPORAL), TIMESTAMP("TIMESTAMP", DatatypeGroup.TEMPORAL), TIMESTAMPTZ("TIMESTAMPTZ", DatatypeGroup.TEMPORAL), INTERVAL("INTERVAL", DatatypeGroup.TEMPORAL),  // Other
    ARRAY("[]", DatatypeGroup.OTHER), JSON("JSON", DatatypeGroup.OTHER), JSONB("JSONB", DatatypeGroup.OTHER), UUID("UUID", DatatypeGroup.OTHER);

    companion object {
        fun from(o: Any?): Datatype? = when (o) {
                is Boolean -> BOOLEAN
                is Int -> INTEGER
                is Long -> BIGINT
                is Float -> FLOAT
                is String -> VARCHAR
            else -> null
        }
    }
}