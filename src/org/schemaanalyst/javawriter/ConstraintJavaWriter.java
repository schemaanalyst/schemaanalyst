package org.schemaanalyst.javawriter;

import java.util.List;

import org.schemaanalyst.sqlrepresentation.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.Column;
import org.schemaanalyst.sqlrepresentation.Constraint;
import org.schemaanalyst.sqlrepresentation.ConstraintVisitor;
import org.schemaanalyst.sqlrepresentation.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.UniqueConstraint;

import static org.schemaanalyst.javawriter.MethodNameConstants.*;

public class ConstraintJavaWriter {

	protected ImportManager importManager;
	protected VariableNameManager variableNameManager;
	
	public ConstraintJavaWriter(ImportManager importManager,
								VariableNameManager variableNameManager) {
		this.importManager = importManager;
		this.variableNameManager = variableNameManager;
	}
	
	public String writeAdditionToTable(String tableVarName, Constraint constraint) {
		
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
				
				String refTableVarName = variableNameManager.getVariableName(constraint.getReferenceTable());
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
		
		return new SchemaWriterContraintVisitor().writeConstraint(tableVarName, constraint);
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
}
