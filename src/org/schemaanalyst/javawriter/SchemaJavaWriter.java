package org.schemaanalyst.javawriter;

import java.util.List;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Constraint;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;

import static org.schemaanalyst.javawriter.MethodNameConstants.*;

public class SchemaJavaWriter {
	
	// schema we are writing Java for
	protected Schema schema;
		
	// managers 
	protected ImportManager importManager;
	protected VariableNameManager variableNameManager;
	
	// sub-writers
	protected ConstraintJavaWriter constraintJavaWriter;
	protected DataTypeJavaWriter dataTypeJavaWriter;	
	
	// local JavaBuilder for pretty output
	protected JavaBuilder jb;
	
	public SchemaJavaWriter(Schema schema) {
		this.schema = schema;
	}
	
	public String writeSchema() {
		return writeSchema(null);
	}
	
	public String writeSchema(String packageName) {
		// initialise	
		importManager = new ImportManager();
		variableNameManager = new VariableNameManager();		
		
		dataTypeJavaWriter = new DataTypeJavaWriter(importManager);
		constraintJavaWriter = new ConstraintJavaWriter(importManager, variableNameManager);
		
		jb = new JavaBuilder();		
		
		// get schema info
		importManager.addImportFor(Schema.class);		
		String schemaClassName = Schema.class.getSimpleName();
		String schemaName = schema.getName();
		
		// start class
		jb.appendln("public class " + schemaName + " extends " + schemaClassName + " {");
		
		// start constructor
		jb.appendln();		
		jb.appendln(1, "public " + schemaName + "() {");		
		jb.appendln(2, "super(\"" + schemaName + "\");");
		
		// write table statements
		List<Table> tables = schema.getTables();
		for (Table table : tables) {
			addTableCode(table);
		} 
		
		// end constructor		
		jb.appendln(1, "}");
		
		// end class
		jb.appendln(0, "}");		

		// get final Java java
		String preamble = getPackageStatement(packageName) + importManager.writeImportStatements(); 
		if (preamble != "") {
			preamble += System.lineSeparator();		
		}
		return preamble + jb.getCode();
	}
	
	protected String getPackageStatement(String packageName) {
		return (packageName == null) ? "" : "package " + packageName + ";" + System.lineSeparator() + System.lineSeparator();
	}	
	
	protected void addTableCode(Table table) {
		
		importManager.addImportFor(table);

		jb.appendln();				
		String className = Table.class.getSimpleName();		
		String tableName = table.getName();
		String tableVarName = variableNameManager.getVariableName(table);
		
		String tableConstruction = className + " " + tableVarName + " = " + 
								   SCHEMA_CREATE_TABLE_METHOD + "(\"" + tableName + "\");";		
		jb.appendln(tableConstruction);
		
		List<Column> columns = table.getColumns();
		for (Column column : columns) {
			addColumnCode(tableVarName, column);
		}
		
		List<Constraint> constraints = table.getConstraints();
		for (Constraint constraint : constraints) {
			jb.appendln(constraintJavaWriter.writeAdditionToTable(tableVarName, constraint));
		}
	}
	
	protected void addColumnCode(String tableVarName, Column column) {
		
		String columnName = column.getName();
		
		String dataTypeConstruction = dataTypeJavaWriter.writeConstructor(column.getDataType());
		
		String statement = tableVarName + "." + TABLE_ADD_COLUMN_METHOD + 
						   "(\"" + columnName + "\", " + dataTypeConstruction + ");";
		
		jb.appendln(statement);
	}
}
