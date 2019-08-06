package me.arrush.javabase.enums;

public enum DataTypeCollection {
    BOOLEAN(Boolean.class),
    NUMERIC(Integer.class),
    CHAR(Character.class),
    STRING(String.class),
    DECIMAL(Float.class),
    TEMPORAL,
    OTHER;

    private final Class<?> c;

    DataTypeCollection(Class<?> c) {
        this.c = c;
    }

    DataTypeCollection() {
        this(null);
    }

    public Class<?> getClazz() { return this.c; }
}
