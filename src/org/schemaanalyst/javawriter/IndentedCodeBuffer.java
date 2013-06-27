package org.schemaanalyst.javawriter;

class IndentedCodeBuffer {

	int indentLevel;
	StringBuilder java;
	
	IndentedCodeBuffer() {
		this(0);
	}
	
	IndentedCodeBuffer(int indentLevel) {
		this.indentLevel = indentLevel;
		java = new StringBuilder();
	}
	
	String getJava() {
		return java.toString();
	}
	
	void prefix(String java) {
		this.java.insert(0, java);
	}	
	
	void appendln() {
		java.append(System.lineSeparator());
	}	

	void appendln(String line) {
		for (int i=0; i < indentLevel; i++) {
			java.append("\t");
		}
		java.append(line);
		appendln();
	}
		
	void appendln(int indentLevel, String line) {
		this.indentLevel = indentLevel;
		appendln(line);
	}
		
	void append(String java) {
		this.java.append(java);
	}	
}
