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
			
	// sub-writers
	protected JavaWriter codeWriter;
	protected ConstraintJavaWriter constraintJavaWriter;
	protected DataTypeJavaWriter dataTypeJavaWriter;	
	protected ExpressionJavaWriter expressionJavaWriter;
	
	// for nice indented output
	protected IndentedCodeBuffer buff;
	
	public SchemaJavaWriter(Schema schema) {
		this.schema = schema;
	}
	
	public String writeSchema() {
		return writeSchema(null);
	}
	
	public String writeSchema(String packageName) {
		// initialise	
		codeWriter = new JavaWriter();
		dataTypeJavaWriter = new DataTypeJavaWriter(codeWriter);
		expressionJavaWriter = new ExpressionJavaWriter(codeWriter);
		constraintJavaWriter = new ConstraintJavaWriter(codeWriter, expressionJavaWriter);
		
		buff = new IndentedCodeBuffer();		
		
		// get schema info
		codeWriter.addImportFor(Schema.class);		
		String schemaClassName = Schema.class.getSimpleName();
		String schemaName = schema.getName();
		
		// start class
		buff.appendln("public class " + schemaName + " extends " + schemaClassName + " {");
		
		// start constructor
		buff.appendln();		
		buff.appendln(1, "public " + schemaName + "() {");		
		buff.appendln(2, "super(\"" + schemaName + "\");");
		
		// write table statements
		List<Table> tables = schema.getTables();
		for (Table table : tables) {
			addTableCode(table);
		} 
		
		// end constructor		
		buff.appendln(1, "}");
		
		// end class
		buff.appendln(0, "}");		

		// get final java code
		String preamble = codeWriter.writePackageStatement(packageName) + 
						  codeWriter.writeImportStatements(); 
		if (preamble != "") {
			preamble += System.lineSeparator();		
		}
		return preamble + buff.getJava();
	}
	
	protected void addTableCode(Table table) {
		// add table creation statement
		String tableCreation = codeWriter.writeMethodCall(
				"this", 
				SCHEMA_CREATE_TABLE_METHOD, 
				codeWriter.writeString(table.getName()));
		
		buff.appendln();		
		buff.appendln(tableCreation + ";");
		
		// add columns
		List<Column> columns = table.getColumns();
		for (Column column : columns) {
			String columnAddition = codeWriter.writeTableMethodCall(
					table, 
					TABLE_ADD_COLUMN_METHOD, 
					codeWriter.writeString(column),
					dataTypeJavaWriter.writeConstruction(column.getDataType())); 
			
			buff.appendln(columnAddition + ";");
		}
	
		// add constraints
		List<Constraint> constraints = table.getConstraints();
		for (Constraint constraint : constraints) {
			buff.appendln(constraintJavaWriter.writeAdditionToTable(table, constraint) + ";");
		}
	}
}
