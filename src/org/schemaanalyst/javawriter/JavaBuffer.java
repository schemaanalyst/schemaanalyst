package org.schemaanalyst.javawriter;

class JavaBuffer {

	int indentLevel;
	String java;
	
	JavaBuffer() {
		this(0);
	}
	
	JavaBuffer(int indentLevel) {
		this.indentLevel = indentLevel;
		java = "";
	}
	
	String getCode() {
		return java;
	}
	
	void prefix(String java) {
		this.java = java + this.java;
	}	
	
	void addln(int indentLevel, String line) {
		this.indentLevel = indentLevel;
		addln(line);
	}
	
	void addln(String line) {
		for (int i=0; i < indentLevel; i++) java += "\t";
		java += line + "\n";
	}
		
	void add(String java) {
		this.java += java;
	}	
	
	void addln() {
		java += "\n";
	}	
}
