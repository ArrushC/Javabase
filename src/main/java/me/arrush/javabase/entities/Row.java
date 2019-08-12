package me.arrush.javabase.entities;

import me.arrush.javabase.DatabaseEntity;
import me.arrush.javabase.annotations.SQLColumn;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A subclass of DatabaseEntity.
 *
 * Stores information about data created inside the
 * database.
 */
@SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
public class Row extends DatabaseEntity {
    /**
     * The table is stored so that when needed, it can
     * be used to perform a query.
     *
     * It is used in {@link Row#update(List) Row#Update(List)}
     * so updating values is easier.
     */
    protected final Table table;
    /**
     * Stores data which makes it resemble a record, full
     * of data.
     *
     * This is a vital component of the {@link me.arrush.javabase.entities.Row Row}
     * class because it allows programmers to store, update
     * or delete a record by using this class.
     */
    protected List<Value<?>> values;
    /**
     * Instantiates the Row class as a new object.
     *
     * Here this constructor is used to create a new object
     * of the {@link Row Row} class. Although the constructor's
     * visibility is public and available everywhere, it should
     * not be used unless you know what your doing, as such,
     * giving provided values.
     *
     * @param table The table.
     * @param values The list of Values provided.
     */
    public Row(Table table, List<Value<?>> values) {
        this.table = table;
        this.values = values;
    }
    /**
     * Creates an instance of the Row class with an overloaded
     * constructor.
     *
     * Here this constructor simply takes in only the table
     * argument, because it is vital to execute operations,
     * as well as provide getters, providing some opportunities.
     *
     * @param table The {@link Table Table}.
     */
    public Row(Table table) {
        this(table, new ArrayList<>());
    }
    /**
     * Returns the table.
     *
     * If programmers want to retrieve the table instance, without
     * having to store a static field of it, they can do so with
     * the row class.
     *
     * @return The {@link Table Table}.
     */
    public Table getTable() { return this.table; }
    /**
     * Returns the list of values.
     *
     * This is made not only for complying with the basic principles
     * of encapsulation, but also to provide the data that is made or
     * fetched.
     *
     * @return the {@link List List} of values.
     */
    public List<Value<?>> getValues() { return this.values; }
    /**
     * Parses the data into a class.
     *
     * Because data may be able to be encoded into a data class,
     * programmers would want to have this class able to parse data
     * into any class, provided that there are annotated fields,
     * using the {@link me.arrush.javabase.annotations.SQLColumn SQLColumn}
     * annotation.
     *
     * @param c The class
     * @throws IllegalAccessException If the field with the annotation
     *                                has a restrictive access modifier.
     * @throws NullPointerException If there is no value for the column
     *                              name provided.
     */
    public void parse(Class<?> c) throws IllegalAccessException, NullPointerException {
        for (Field f : Arrays.stream(c.getDeclaredFields()).filter(f -> !Modifier.isFinal(f.getModifiers())).collect(Collectors.toList())) {
            String columnName = f.getAnnotation(SQLColumn.class).column();
            Object value = Objects.requireNonNull(this.values.stream().filter(v -> v.getColumn().getName()
                    .equals(columnName)).findFirst().orElse(null));
            f.setAccessible(true);
            f.set(c, value);
        }
    }
    /**
     * Modifies the list without sending an update request to the
     * database.
     *
     * @param action A lambda, that takes in the values list, and
     *               then substitutes it with the actual list.
     * @return The modified {@link Row Row} instance.
     */
    public Row modify(Function<List<Value<?>>, List<Value<?>>> action) {
        this.values = action.apply(this.values);
        return this;
    }
    /**
     * Frankly modifies the list, with the new set of values.
     *
     * @param values The new list of values.
     * @return The modified {@link Row Row} instance.
     */
    public Row modify(List<Value<?>> values) {
        return this.modify(v -> values);
    }
    /**
     * Sends an update request to the database, with the new
     * and old list of values.
     *
     * Although the visibility is suggested to be private,
     * because the values in the list may be modified but not
     * executed at all, hence this method allows programmers
     * to directly update the values in the database.
     *
     * @param old The old list of values so they can be used
     *            to compare values in the conditional clause.
     * @return The {@link Row Row} instance.
     */
    public Row update(List<Value<?>> old) {
        String setValue = this.values.stream().map(Value::toString).collect(Collectors.joining(", "));
        String conditionValue = old.stream().map(Value::asConditionString).collect(Collectors.joining(" && "));
        return this.table.update(this).modifyStatement(s -> String.format(s, this.table.getName(), setValue, conditionValue)).doAndReturn();
    }
    /**
     * Updates the list of values and then updates the values
     * in the database.
     *
     * @param action A lambda, that takes in the values list, and
     *               then substitutes it with the actual list.
     * @return The modified {@link Row Row} instance.
     */
    public Row updateValues(Function<List<Value<?>>, List<Value<?>>> action) {
        List<Value<?>> old = this.values;
        this.values = action.apply(this.values);
        return this.update(old);
    }
    /**
     * Updates the list of values, with a new set of values,
     * and updates them in the database as well.
     *
     * @param values The new list of values.
     * @return The modified {@link Row Row} instance.
     */
    public Row updateValues(List<Value<?>> values) {
        return this.updateValues(v -> values);
    }
    /**
     * Removes the row from the table and dereferences
     * the instance field, preparing for garbage collection.
     */
    public void remove() {
        // Remove from table. code.
        this.values = null;
    }
    /**
     * Inserts the data of this row into the database,
     * provided that this is instantiated manually.
     *
     * @return The {@link Row Row} instance.
     */
    public Row insert() {
        String columns = this.values.stream().map(v -> v.getColumn().getName()).collect(Collectors.joining(","));
        String values = this.values.stream().map(Value::valueToString).collect(Collectors.joining(","));
        return this.table.insert(this).modifyStatement(s -> String.format(s, this.table.getName(), columns, values)).doAndReturn();
    }

    public Value<?> getValue(String column) { return this.values.stream().filter(v -> v.getColumn().getName().equals(column)).findFirst().orElse(null); }

    public Object getObject(String column) { return this.getValue(column).getValue(); }

    public String getString(String column) { return (String) this.getObject(column); }

    public int getInt(String column) { return (int) this.getObject(column); }

    public long getLong(String column) { return (long) this.getObject(column);}

    public boolean getBoolean(String column) { return (boolean) this.getObject(column); }


}
