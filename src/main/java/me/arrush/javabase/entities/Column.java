package me.arrush.javabase.entities;

import me.arrush.javabase.enums.ConstraintColumn;
import me.arrush.javabase.enums.DataType;
import me.arrush.javabase.enums.DataTypeCollection;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Shares the same behaviour as a column in the database.
 *
 * This must be explicitly instantiated and kept so it can
 * be used for tables.
 *
 * @see Row
 * @see Table
 * @see Value
 */
public final class Column {
    /**
     * The name of the column.
     */
    private final String name;
    /**
     * The constraints of the column, that indicates the behaviour
     * of the values.
     */
    private final List<ConstraintColumn> constraints;
    /**
     * The actual data type that the value belongs to.
     */
    private final DataTypeCollection datatypeCollection;
    /**
     * The name of the specified datatype.
     */
    private String dataType;
    /**
     *
     *
     * @param name
     * @param dataType
     * @param constraints
     */
    public Column(String name, DataType dataType, List<ConstraintColumn> constraints) {
        this.name = name;
        this.constraints = constraints;
        this.datatypeCollection = dataType.getCollection();
        this.dataType = dataType.getType();
    }

    public Column(String name, DataType dataType) {
        this(name, dataType, Collections.emptyList());
    }

    public Column modifyDataType(Function<String, String> action) {
        this.dataType = action.apply(this.dataType);
        return this;
    }

    public String getName() {
        return this.name;
    }

    public String getDataType() {
        return this.dataType;
    }

    public DataTypeCollection getDataTypeCollection() {
        return this.datatypeCollection;
    }

    public boolean equals(Column column) {
        return Objects.equals(this.name, column.name);
    }

    @Override
    public String toString() {
        return this.name + " " + this.dataType + this.constraintsToString();
    }

    private String constraintsToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.constraints.size(); i++) {
            sb.append(this.constraints.get(i));
            if (i < this.constraints.size() - 1) sb.append(" ");
        }
        return sb.toString();
    }
}
