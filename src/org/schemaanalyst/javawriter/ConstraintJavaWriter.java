package org.schemaanalyst.javawriter;

import java.util.ArrayList;
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

	protected JavaWriter javaWriter;
	protected ExpressionJavaWriter expressionJavaWriter;
	
	public ConstraintJavaWriter(JavaWriter javaWriter,
								ExpressionJavaWriter expressionJavaWriter) {
		this.javaWriter = javaWriter;
		this.expressionJavaWriter = expressionJavaWriter;
	}
	
	public String writeAdditionToTable(Table table, Constraint constraint) {
		
		class WriterContraintVisitor implements ConstraintVisitor {

			Table table;
			String methodName;
			List<String> args;
			
			String writeConstraint(Table table, Constraint constraint) {
				this.table = table;
				args = new ArrayList<String>();
				if (constraint.hasName()) {
					args.add(javaWriter.writeString(constraint.getName()));
				}				
				constraint.accept(this);
				return javaWriter.writeTableMethodCall(table, methodName, args);
			}
			
			public void visit(CheckConstraint constraint) {
				methodName = TABLE_ADD_CHECK_CONSTRAINT_METHOD;				
				args.add(expressionJavaWriter.writeConstruction(constraint.getExpression()));
			}

			public void visit(ForeignKeyConstraint constraint) {				
				methodName = TABLE_ADD_FOREIGN_KEY_CONSTRAINT_METHOD;
				
				args.add(wrapColumnArgsString(table, constraint.getColumns()));
				args.add(javaWriter.writeString(constraint.getReferenceTable().getName()));
				args.add(wrapColumnArgsString(table, constraint.getReferenceColumns()));	
			}

			public void visit(NotNullConstraint constraint) {		
				methodName = TABLE_ADD_NOT_NULL_CONSTRAINT_METHOD;				
				args.add(javaWriter.writeGetColumn(constraint.getColumn()));				
			}

			public void visit(PrimaryKeyConstraint constraint) {
				methodName = TABLE_SET_PRIMARY_KEY_CONSTRAINT_METHOD;
				addColumnArgs(table, constraint.getColumns());				
			} 

			public void visit(UniqueConstraint constraint) {				
				methodName = TABLE_ADD_UNIQUE_CONSTRAINT_METHOD;
				addColumnArgs(table, constraint.getColumns());				
			}	
			
			void addColumnArgs(Table table, List<Column> columns) {
				args.addAll(makeColumnArgsList(table, columns));
			}			
			
			List<String> makeColumnArgsList(Table table, List<Column> columns) {
				List<String> columnArgs = new ArrayList<String>();
				for (Column column : columns) {
					columnArgs.add(javaWriter.writeGetColumn(column));
				}
				return columnArgs;
			}

			String wrapColumnArgsString(Table table, List<Column> columns) {
				String columnArgsString = makeColumnArgsString(table, columns);
				if (columns.size() > 2) {
					return javaWriter.writeMethodCall(
							Table.class.getSimpleName(), 
							TABLE_MAKE_COLUMN_LIST_METHOD, 
							columnArgsString);
				} else {
					return columnArgsString;
				}
			}
			
			String makeColumnArgsString(Table table, List<Column> columns) {
				List<String> columnArgs = makeColumnArgsList(table, columns);
				return javaWriter.writeArgsList(columnArgs);
			}
			
		}
		
		return (new WriterContraintVisitor()).writeConstraint(table, constraint);
	}		
}
