package org.schemaanalyst.sqlwriter;

import org.schemaanalyst.sqlrepresentation.CheckConstraint;
import org.schemaanalyst.sqlrepresentation.Constraint;
import org.schemaanalyst.sqlrepresentation.ConstraintVisitor;
import org.schemaanalyst.sqlrepresentation.ForeignKeyConstraint;
import org.schemaanalyst.sqlrepresentation.NotNullConstraint;
import org.schemaanalyst.sqlrepresentation.PrimaryKeyConstraint;
import org.schemaanalyst.sqlrepresentation.UniqueConstraint;
import org.schemaanalyst.sqlrepresentation.expression.Expression;

import static org.schemaanalyst.sqlwriter.SQLWriter.writeColumnList;

public class ConstraintSQLWriter {
	
	protected ExpressionSQLWriter expressionSQLWriter;	
	protected CheckConditionSQLWriter checkConditionSQLWriter;
	
	public void setExpressionSQLWriter(ExpressionSQLWriter expressionSQLWriter) {
		this.expressionSQLWriter = expressionSQLWriter;
	}
	
	public void setCheckConditionSQLWriter(CheckConditionSQLWriter predicateSQLWriter) {
		this.checkConditionSQLWriter = predicateSQLWriter;
	}
	
	public String writeConstraint(Constraint constraint) {
		
		class ConstraintSQLWriterVisitor implements ConstraintVisitor {
			String sql;
			
			public String writeConstraint(Constraint constraint) {
				sql = "";
				constraint.accept(this);
				return sql;
			}

			public void visit(CheckConstraint constraint) {
				sql = writeCheck(constraint);
			}

			public void visit(ForeignKeyConstraint constraint) {
				sql = writeForeignKey(constraint);
			}

			public void visit(NotNullConstraint constraint) {
				sql = writeNotNull(constraint);
			}

			public void visit(PrimaryKeyConstraint constraint) {
				sql = writePrimaryKey(constraint);
			}

			public void visit(UniqueConstraint constraint) {
				sql = writeUnique(constraint);
			}			
		}
		
		String sql = writeConstraintName(constraint);		
		sql += (new ConstraintSQLWriterVisitor()).writeConstraint(constraint);
		return sql;
	}
	
	public String writeCheck(CheckConstraint check) {
		Expression expression = check.getExpression();
		if (expression != null) {
			return "CHECK (" + expressionSQLWriter.writeExpression(check.getExpression()) + ")";
		} else {
			return "CHECK (" + checkConditionSQLWriter.writeCheckCondition(check.getCheckCondition()) + ")";
		}
	}
	
	public String writeForeignKey(ForeignKeyConstraint foreignKey) {
		String sql = "";
		if (foreignKey.hasMultipleColumns()) {
			sql += "FOREIGN KEY (" + writeColumnList(foreignKey.getColumns()) + ")";
		}
		sql += " REFERENCES " + foreignKey.getReferenceTable().getName() + 
		       " (" + SQLWriter.writeColumnList(foreignKey.getReferenceColumns()) + ")";
		return sql;
	}
	
	public String writeNotNull(NotNullConstraint notNull) {
		return "NOT NULL";
	}
	
	public String writePrimaryKey(PrimaryKeyConstraint primaryKey) {
		String sql = "PRIMARY KEY";
		if (primaryKey.hasMultipleColumns()) {
			sql += " (" + writeColumnList(primaryKey.getColumns()) + ")";
		}
		return sql;					
	}	
	
	public String writeUnique(UniqueConstraint unique) {
		String sql = "UNIQUE";
		if (unique.hasMultipleColumns()) {
			sql += " (" + writeColumnList(unique.getColumns()) + ")";
		}
		return sql;
	}
	
	public String writeConstraintName(Constraint constraint) {
		if (constraint.hasName()) {
			return "CONSTRAINT " + constraint.getName() + " ";			
		} else {
			return "";
		}
	}
}