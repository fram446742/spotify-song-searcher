package com.ucjc.utils;

public class TError {
    private String lexeme;
    private String type;
    private String description;
    private int line;
    private int column;

    public TError(String le, int li, int co, String t, String de) {
        lexeme = le;
        line = li;
        column = co;
        type = t;
        description = de;
    }

    public String getLexeme() {
        return this.lexeme;
    }

    public String getType() {
        return this.type;
    }

    public String getDescription() {
        return this.description;
    }

    public int getLine() {
        return this.line;
    }

    public int getColumn() {
        return this.column;
    }

}
