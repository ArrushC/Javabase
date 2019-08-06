package me.arrush.javabase.entities;

import java.util.List;

/**
 * Stores information about data as a single entity.
 *
 * Because columns contain data, there must be a way to
 * present this data in the same manner as a data class
 * would.
 *
 * @param <T> The data type of the value.
 */
@SuppressWarnings("WeakerAccess")
public final class Value<T> {
    /**
     * The <b>actual</b> value stored.
     */
    private T value;
    /**
     * The column of the value.
     */
    private Column column;
    /**
     * Creates a {@link Value Value} instance.
     *
     * @param value The <b>actual</b> value.
     * @param column The column in which the value is held.
     */
    public Value(T value, Column column) {
        this.value = value;
        this.column = column;
    }
    /**
     * Returns the actual value.
     *
     * @return The actual value.
     */
    public T getValue() { return this.value; }
    /**
     * Sets a new value for the field.
     *
     * @param value The new value.
     * @return The modified {@link Value Value} instance
     */
    public Value<T> setValue(T value) {
        this.value = value;
        return this;
    }

    public Column getColumn() { return this.column; }
    public Value<T> setColumn(Column column) {
        this.column = column;
        return this;
    }

    @Override
    public String toString() { return column.getName() + " = " + this.valueToString(); }
    public String asConditionString() { return column.getName() + " == " + this.valueToString(); }
    public String valueToString() { return ((value instanceof String || value instanceof List) ? "\'" + value.toString() + "\'" : value.toString()); }
}
