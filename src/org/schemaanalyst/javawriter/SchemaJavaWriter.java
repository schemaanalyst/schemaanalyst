package org.schemaanalyst.javawriter;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.DataType;
import org.schemaanalyst.sqlrepresentation.datatype.DataTypeCategoryVisitor;
import org.schemaanalyst.sqlrepresentation.datatype.LengthLimited;
import org.schemaanalyst.sqlrepresentation.datatype.PrecisionedAndScaled;
import org.schemaanalyst.sqlrepresentation.datatype.Signed;

public class SchemaJavaWriter {

	static final String ADD_COLUMN_TO_TABLE_METHOD_NAME = "addColumn";
	
	protected Set<String> imports;		
	protected int indentLevel;
	protected String java;
	
	protected Schema schema;
	
	public SchemaJavaWriter(Schema schema) {
		this.schema = schema;
	}
	
	public String writeSchema() {
		return writeSchema(null);
	}
	
	public String writeSchema(String packageName) {
		// initialise
		java = "";		
		imports = new TreeSet<String>();		
			
		// get schema info
		addImport(Schema.class);		
		String schemaClassName = Schema.class.getSimpleName();
		String schemaName = schema.getName();
		
		// start class
		write("public class " + schemaName + " extends " + schemaClassName + " {");
		
		// start constructor
		writeNewLine();		
		write(1, "public " + schemaName + "() {");		
		write(2, "super(\"" + schemaName + "\");");
		
		// write table statements
		List<Table> tables = schema.getTables();
		for (Table table : tables) {
			writeTableStatements(table);
		}
		
		// end constructor		
		write(1, "}");
		
		// end class
		write(0, "}");		

		prefixImportStatements();
		prefixPackageStatement(packageName);
		
		return java;
	}
	
	protected void writeTableStatements(Table table) {
		
		addImport(Table.class);

		writeNewLine();				
		
		String className = Table.class.getSimpleName();
		String tableName = table.getName();
		String tableVarName = getJavaVariableName("table", tableName);
		
		String tableConstruction = className + " " + tableVarName + " = new " + className + "(\"" + tableName + "\");";		
		write(tableConstruction);
		
		List<Column> columns = table.getColumns();
		for (Column column : columns) {
			writeColumnStatement(tableVarName, column);
		}
	}
	
	protected void writeColumnStatement(String tableVarName, Column column) {
		
		String columnName = column.getName();
		
		
		String statement = tableVarName + "." + ADD_COLUMN_TO_TABLE_METHOD_NAME + 
						   "(\"" + columnName + "\", " + constructDataTypeCode(column.getType()) + ");";
		
		write(statement);
	}
	
	protected String constructDataTypeCode(DataType dataType) {
		
		class SchemaWriterDataTypeCategoryVisitor implements DataTypeCategoryVisitor {

			String code = "";
			String getParameters(DataType type) {
				type.accept(this);
				return "(" + code + ")";
			}
			
			public void visit(DataType type) {
				// do nothing
			}

			public void visit(LengthLimited type) {
				code += type.getLength();
			}

			public void visit(PrecisionedAndScaled type) {
				Integer precision = type.getPrecision(); 
				if (precision != null) {
					code += precision;
					Integer scale = type.getScale();
					if (scale != null) {
						code += ", " + scale;
					}
				}
			}

			public void visit(Signed type) {
				boolean isSigned = type.isSigned();
				if (!isSigned) {
					code += "false";
				}
			}
		}
		
		
		addImport(dataType.getClass());
		String dataTypeClassName = dataType.getClass().getSimpleName();
		return "new " + dataTypeClassName + new SchemaWriterDataTypeCategoryVisitor().getParameters(dataType);
	}
	
	protected void prefixImportStatements() {
		String bodyCode = java;
		
		java = "";
		for (String classToImport : imports) {
			write(0, "import " + classToImport + ";");
		}
		writeNewLine();
		
		java += bodyCode; 
	}
	
	protected void prefixPackageStatement(String packageName) {
		String bodyCode = java;
		
		java = "";
		// write package name
		if (packageName != null) {
			write("package " + packageName + ";");
			writeNewLine();
		}	
		
		java += bodyCode;
	}
	
	protected void addImport(Class<?> javaClass) {
		imports.add(javaClass.getCanonicalName().toString());		
	}
	
	protected void setIndentLevel(int indentLevel) {
		this.indentLevel = indentLevel;
	}
	
	protected void write(int indentLevel, String line) {
		setIndentLevel(indentLevel);
		write(line);
	}
	
	protected void write(String line) {
		for (int i=0; i < indentLevel; i++) java += "\t";
		java += line + "\n";			
	}
	
	protected void writeNewLine() {
		java += "\n";
	}
	
	protected String getJavaVariableName(String prefix, String originalIdentifier) {
		// TODO - camel case the original identifier to make it neater ...
		return prefix + originalIdentifier;
	}
}
