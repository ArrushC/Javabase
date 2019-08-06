package me.arrush.javabase.enums;

public enum ConstraintColumn {

    NOT_NULL("NOT NULL"),
    UNIQUE("UNIQUE"),
    PRIMARY_KEY("PRIMARY KEY"),
    FOREIGN_KEY("FOREIGN KEY"),
    CHECK("CHECK(%s)");

    private final String constraint;

    ConstraintColumn(String constraint) {
        this.constraint = constraint;
    }

    public String getConstraint() {
        return this.constraint;
    }
}
