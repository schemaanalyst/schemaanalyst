package org.schemaanalyst.javawriter;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Table;

import static org.schemaanalyst.javawriter.MethodNameConstants.*;

public class JavaWriter {

	protected static final String TABLE_VAR_PREFIX = "table";	
	
	protected SortedSet<String> imports;
	
	public JavaWriter() {
		imports = new TreeSet<String>();
	}
	
	
	/**** IMPORT MANAGEMENT ***/
	
	public void addImportFor(Object object) {
		addImportFor(object.getClass());
	}	
	
	public void addImportFor(Class<?> javaClass) {
		imports.add(javaClass.getCanonicalName().toString());		
	}	
		
	public String writeImportStatements() {
		StringBuilder java = new StringBuilder();
		for (String classToImport : imports) {
			java.append("import " + classToImport + ";" + System.lineSeparator());
		}		
		return java.toString();
	}		
	
	
	/*** GENERIC STATEMENTS ***/
	
	public String writePackageStatement(String packageName) {
		return (packageName == null) ? "" : "package " + packageName + ";" + System.lineSeparator() + System.lineSeparator();
	}		
	
	public String writeAssignment(Class<?> javaClass, String variable, String target) {
		addImportFor(javaClass);
		return javaClass.getSimpleName() + " = " + target;
	}
	
	public String writeConstruction(Object object, String... args) {
		return writeConstruction(object, Arrays.asList(args));		
	}
	
	public String writeConstruction(Object object, List<String> args) {
		addImportFor(object);
		return "new " + object.getClass().getSimpleName() + "(" + writeArgsList(args) + ")";
	}
	
	public String writeEnumValue(Enum<?> enumValue) {		
		addImportFor(enumValue.getDeclaringClass());
		return enumValue.getDeclaringClass().getSimpleName() + "." + enumValue.name();		
	}
	
	public String writeMethodCall(String target, String methodName, String... args) {
		return writeMethodCall(target, methodName, Arrays.asList(args));
	}	
	
	public String writeMethodCall(String target, String methodName, List<String> args) {
		String java = "";
		if (target != null) {
			java += target + ".";
		}
		java += methodName + "(" + writeArgsList(args) + ")";
		return java;
	}
			
	public String writeArgsList(List<String> args) {
		StringBuilder java = new StringBuilder();
		boolean first = true;
		for (String arg : args) {
			if (first) {
				first = false;
			} else {
				java.append(", ");
			}
			java.append(arg);
		}
		return java.toString();
	}
	
	/*** PRIMITIVE TYPES AND STRINGS ***/
	
	public String writeBoolean(boolean toWrite) {
		return toWrite ? "true" : "false";
	}	
	
	public String writeString(String string) {
		return "\"" + string + "\"";
	}	
	
	
	/*** REPRESENTATION-SPECIFIC CODE ***/
	
	public String writeTableMethodCall(Table table, String methodName, String... args) {
		return writeTableMethodCall(table, methodName, Arrays.asList(args));	
	}
	
	public String writeTableMethodCall(Table table, String methodName, List<String> args) {
		return writeMethodCall(getVariableName(table), methodName, args);		
	}
	
	public String writeGetColumn(Column column) {
		return writeTableMethodCall(column.getTable(), TABLE_GET_COLUMN_METHOD, writeString(column));		
	}	
	
	public String writeString(Column column) {
		return writeString(column.getName());
	}	
	
	public String writeString(Table table) {
		return writeString(table.getName());
	}
	
	public String getVariableName(Table table) {
		// TODO - camel case the original identifier to make it neater ...
		return TABLE_VAR_PREFIX + table.getName();
	}
}
