package org.schemaanalyst.util;

public class IndentableStringBuilder {

    protected int indentLevel;
    protected StringBuilder sb;

    public IndentableStringBuilder() {
        this(0);
    }

    public IndentableStringBuilder(int indentLevel) {
        this.indentLevel = indentLevel;
        sb = new StringBuilder();
    }

    public void prepend(String str) {
        this.sb.insert(0, str);
    }

    public void appendln(String line) {
        indent();
        append(line);
        appendln();
    }

    public void appendln(int indentLevel, String line) {
        setIndentLevel(indentLevel);
        appendln(line);
    }

    public void append(String str) {
        this.sb.append(str);
    }

    public void append(int indentLevel, String str) {
        indent(indentLevel);
        append(str);
    }

    public void appendln(int indentLevel) {
        indent(indentLevel);
        appendln();
    }

    public void appendln() {
        sb.append(System.lineSeparator());
    }

    public void appendTab() {
        sb.append("\t");
    }

    public void appendTabbed(String str) {
        appendTab();
        append(str);
    }

    public void indent(int indentLevel) {
        setIndentLevel(indentLevel);
        indent();
    }

    public void indent() {
        for (int i = 0; i < indentLevel; i++) {
            sb.append("\t");
        }
    }

    public void setIndentLevel(int indentLevel) {
        this.indentLevel = indentLevel;
    }

    public int getIndentLevel() {
        return indentLevel;
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
