package org.schemaanalyst.util;

public class IndentableStringBuilder {

	protected int indentLevel;
	protected StringBuilder str;
	
	public IndentableStringBuilder() {
		this(0);
	}
	
	public IndentableStringBuilder(int indentLevel) {
		this.indentLevel = indentLevel;
		str = new StringBuilder();
	}
	
	public void prepend(String str) {
		this.str.insert(0, str);
	}	
	
	public void appendln() {
		str.append(System.lineSeparator());
	}	

	public void appendln(String line) {
		for (int i=0; i < indentLevel; i++) {
			str.append("\t");
		}
		str.append(line);
		appendln();
	}
		
	public void appendln(int indentLevel, String line) {
		this.indentLevel = indentLevel;
		appendln(line);
	}
		
	public void append(String str) {
		this.str.append(str);
	}	
	
	public String toString() {
		return str.toString();
	}
}
