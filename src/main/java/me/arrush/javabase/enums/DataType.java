package me.arrush.javabase.enums;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public enum DataType {
    // Boolean data type
    BOOLEAN("boolean", DataTypeCollection.BOOLEAN),

    // Character data types. VARCHAR(n) where n is the amount of characters.
    CHAR("CHAR(%s)", DataTypeCollection.CHAR),
    VARCHAR("VARCHAR(%s)", DataTypeCollection.STRING),
    TEXT("TEXT", DataTypeCollection.STRING),

    // Numeric data types
    SMALLINT("SMALLINT", DataTypeCollection.NUMERIC),
    INTEGER("INT", DataTypeCollection.NUMERIC),
    SERIAL("SERIAL", DataTypeCollection.NUMERIC),
    BIGINT("BIGINT", DataTypeCollection.NUMERIC),

    // Floating-point Number data types
    FLOAT("FLOAT(%s)", DataTypeCollection.DECIMAL),
    REAL("REAL", DataTypeCollection.DECIMAL), FLOAT8("FLOAT8", DataTypeCollection.DECIMAL),
    NUMERIC("NUMERIC(%s, %s)", DataTypeCollection.DECIMAL), // (p, s) = real number with p digits with s number after the decimal point. Being the exact number

    // Temporal data types
    DATE("DATE", DataTypeCollection.TEMPORAL),
    TIME("TIME", DataTypeCollection.TEMPORAL),
    TIMESTAMP("TIMESTAMP", DataTypeCollection.TEMPORAL),
    TIMESTAMPTZ("TIMESTAMPTZ", DataTypeCollection.TEMPORAL),
    INTERVAL("INTERVAL", DataTypeCollection.TEMPORAL),

    // Other
    ARRAY("[]", DataTypeCollection.OTHER),
    JSON("JSON", DataTypeCollection.OTHER),
    JSONB("JSONB", DataTypeCollection.OTHER),
    UUID("UUID", DataTypeCollection.OTHER);

    private final String type;
    private final DataTypeCollection collection;

    DataType(String type, DataTypeCollection collection) {
        this.type = type;
        this.collection = collection;
    }

    public String getType() {
        return this.type;
    }
    public DataTypeCollection getCollection() { return this.collection; }

    public static DataType from(Object o) {
        if (o instanceof Boolean) return BOOLEAN;
        else if (o instanceof Integer) return INTEGER;
        else if (o instanceof Long) return SERIAL;
        else if (o instanceof Float) return FLOAT;
        else if (o instanceof String) return VARCHAR;
        // I would be appreciated if you can help me to add more instanceof checks here.
        return null;
    }
}
