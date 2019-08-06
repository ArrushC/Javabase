package me.arrush.javabase.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation used to annotate fields of a class.
 *
 * When programmers want to parse data from the
 * {@link me.arrush.javabase.entities.Row Row} class to
 * a class of their own, they must annotate their
 * selected fields with this annotation, and setting the
 * column name, which is reflected as the actual column
 * in the table and the row itself.
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLColumn {
    /**
     * The column name that is reflected as the actual
     * column in the table.
     *
     * @return The column name.
     */
    String column();
}
