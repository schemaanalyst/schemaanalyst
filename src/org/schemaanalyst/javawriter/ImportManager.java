package org.schemaanalyst.javawriter;

import java.util.SortedSet;
import java.util.TreeSet;

public class ImportManager {

	protected SortedSet<String> imports;
	
	public ImportManager() {
		imports = new TreeSet<String>();
	}
	
	public void addImportFor(Object object) {
		addImportFor(object.getClass());
	}	
	
	public void addImportFor(Class<?> javaClass) {
		imports.add(javaClass.getCanonicalName().toString());		
	}	
	
	public String writeImportStatements() {
		String java = "";
		for (String classToImport : imports) {
			java += "import " + classToImport + ";\n";
		}		
		return java;
	}	
}
