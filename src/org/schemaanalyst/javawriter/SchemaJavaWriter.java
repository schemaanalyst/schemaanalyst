package org.schemaanalyst.javawriter;

import java.util.List;

import org.schemaanalyst.sqlrepresentation.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Constraint;
import org.schemaanalyst.sqlrepresentation.ConstraintVisitor;
import org.schemaanalyst.sqlrepresentation.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.UniqueConstraint;
import org.schemaanalyst.sqlrepresentation.datatype.DataType;
import org.schemaanalyst.sqlrepresentation.datatype.DataTypeCategoryVisitor;
import org.schemaanalyst.sqlrepresentation.datatype.LengthLimited;
import org.schemaanalyst.sqlrepresentation.datatype.PrecisionedAndScaled;
import org.schemaanalyst.sqlrepresentation.datatype.Signed;

public class SchemaJavaWriter {

	// variable name prefixes
	static final String TABLE_VAR_PREFIX = "table";
	
	// method call names
	static final String SCHEMA_CREATE_TABLE_METHOD = "createTable",
						TABLE_ADD_COLUMN_METHOD = "addColumn",
						TABLE_GET_COLUMN_METHOD = "getColumn",
						TABLE_MAKE_COLUMN_LIST_METHOD = "makeColumnList",
						TABLE_ADD_FOREIGN_KEY_CONSTRAINT_METHOD = "addForeignKeyConstraint",
						TABLE_ADD_NOT_NULL_CONSTRAINT_METHOD = "addNotNullConstraint",
						TABLE_SET_PRIMARY_KEY_CONSTRAINT_METHOD = "setPrimaryKeyConstraint",
						TABLE_ADD_UNIQUE_CONSTRAINT_METHOD = "addUniqueConstraint";

	protected Schema schema;
	protected ImportManager importManager;
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
			preamble += "\n";		
		}
		return preamble + jb.getCode();
	}
	
	protected void addTableCode(Table table) {
		
		importManager.addImportFor(table);

		jb.appendln();				
		String className = Table.class.getSimpleName();		
		String tableName = table.getName();
		String tableVarName = getTableVariableName(table);
		
		String tableConstruction = className + " " + tableVarName + " = " + 
								   SCHEMA_CREATE_TABLE_METHOD + "(\"" + tableName + "\");";		
		jb.appendln(tableConstruction);
		
		List<Column> columns = table.getColumns();
		for (Column column : columns) {
			addColumnCode(tableVarName, column);
		}
		
		List<Constraint> constraints = table.getConstraints();
		for (Constraint constraint : constraints) {
			addConstraintCode(tableVarName, constraint);
		}
	}
	
	protected void addColumnCode(String tableVarName, Column column) {
		
		String columnName = column.getName();
		
		
		String statement = tableVarName + "." + TABLE_ADD_COLUMN_METHOD + 
						   "(\"" + columnName + "\", " + writeDataTypeConstruction(column.getType()) + ");";
		
		jb.appendln(statement);
	}
	
	protected String writeDataTypeConstruction(DataType dataType) {
		
		class SchemaWriterDataTypeCategoryVisitor implements DataTypeCategoryVisitor {

			String java;
			
			String writeParams(DataType type) {
				java = "";
				type.accept(this);
				return "(" + java + ")";
			}
			
			public void visit(DataType type) {
				// no params for standard types -- do nothing
			}

			public void visit(LengthLimited type) {
				Integer length = type.getLength();
				if (length != null) {
					java += type.getLength();
				}
			}

			public void visit(PrecisionedAndScaled type) {
				Integer precision = type.getPrecision(); 
				if (precision != null) {
					java += precision;
					Integer scale = type.getScale();
					if (scale != null) {
						java += ", " + scale;
					}
				}
			}

			public void visit(Signed type) {
				boolean isSigned = type.isSigned();
				if (!isSigned) {
					java += "false";
				}
			}
		}
		
		importManager.addImportFor(dataType);
		String dataTypeClassName = dataType.getClass().getSimpleName();
		return "new " + dataTypeClassName + new SchemaWriterDataTypeCategoryVisitor().writeParams(dataType);
	}
	
	protected void addConstraintCode(String tableVarName, Constraint constraint) {
		
		class SchemaWriterContraintVisitor implements ConstraintVisitor {

			String tableVarName, java;
			
			String writeConstraint(String tableVarName, Constraint constraint) {
				this.tableVarName = tableVarName;
				java = "";
				constraint.accept(this);				
				return java;
			}
			
			public void visit(CheckConstraint constraint) {
				// TODO: add java for check constraints ...
			}

			public void visit(ForeignKeyConstraint constraint) {
				java =  writeMethodCall(TABLE_ADD_FOREIGN_KEY_CONSTRAINT_METHOD)  + "(";				
				
				java += writeConstraintName(constraint);								
				java += writeGetColumnListCode(tableVarName, constraint.getColumns(), true);
				java += ", ";
				
				String refTableVarName = getTableVariableName(constraint.getReferenceTable());
				java += refTableVarName + ", ";
				java += writeGetColumnListCode(refTableVarName, constraint.getReferenceColumns(), true);
				
				java += ");";								
			}

			public void visit(NotNullConstraint constraint) {				
				java =  writeMethodCall(TABLE_ADD_NOT_NULL_CONSTRAINT_METHOD)  + "(";				
				java += writeConstraintName(constraint);								
				java += writeGetColumnCode(tableVarName, constraint.getColumn());
				java += ");";				
			}

			public void visit(PrimaryKeyConstraint constraint) {
				java =  writeMethodCall(TABLE_SET_PRIMARY_KEY_CONSTRAINT_METHOD)  + "(";				
				java += writeConstraintName(constraint);								
				java += writeGetColumnListCode(tableVarName, constraint.getColumns());
				java += ");";
			} 

			public void visit(UniqueConstraint constraint) {				
				java =  writeMethodCall(TABLE_ADD_UNIQUE_CONSTRAINT_METHOD)  + "(";				
				java += writeConstraintName(constraint);				
				java += writeGetColumnListCode(tableVarName, constraint.getColumns());
				java += ");";				
			}	
			
			String writeConstraintName(Constraint constraint) {
				return constraint.hasName() ? "\"" + constraint.getName() + "\", " : "";
			}
			
			String writeMethodCall(String methodName) {
				return tableVarName + "." + methodName;
			}
		}
		
		String java = new SchemaWriterContraintVisitor().writeConstraint(tableVarName, constraint);
		jb.appendln(java);
	}
	
	protected String writeGetColumnCode(String tableVarName, Column column) {
		return tableVarName + "." + TABLE_GET_COLUMN_METHOD + "(\"" + column.getName() + "\")";
	}
	
	protected String writeGetColumnListCode(String tableVarName, List<Column> columns) {
		return writeGetColumnListCode(tableVarName, columns, false);
	}
	
	protected String writeGetColumnListCode(String tableVarName, List<Column> columns, boolean wrapInMethod) {
		String java = "";
		
		boolean doWrapInMethod = wrapInMethod && columns.size() > 2; 
		if (doWrapInMethod) {
			java = Table.class.getSimpleName() + "." + TABLE_MAKE_COLUMN_LIST_METHOD + "(";
		}
		
		boolean first = true;
		for (Column column : columns) {
			if (first) {
				first = false;
			} else {
				java += ", ";
			}
			java += writeGetColumnCode(tableVarName, column);
		}
		
		if (doWrapInMethod) {
			java += ")";
		}
		
		return java;
	}
	
	protected String getPackageStatement(String packageName) {
		return (packageName == null) ? "" : "package " + packageName + ";\n\n";
	}
	
	protected String getTableVariableName(Table table) {
		return getJavaVariableName(TABLE_VAR_PREFIX, table.getName());
	}
	
	protected String getJavaVariableName(String prefix, String originalIdentifier) {
		// TODO - camel case the original identifier to make it neater ...
		return prefix + originalIdentifier;
	}
}
