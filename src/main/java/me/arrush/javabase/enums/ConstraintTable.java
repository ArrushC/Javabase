package me.arrush.javabase.enums;

public enum ConstraintTable {
    ; // What ?

    private final String constraint;

    ConstraintTable(String constraint) {
        this.constraint = constraint;
    }

    public String getConstraint() {
        return this.constraint;
    }
}
