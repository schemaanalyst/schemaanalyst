package org.schemaanalyst.sqlwriter;

import org.schemaanalyst.schema.CheckConstraint;
import org.schemaanalyst.schema.ForeignKeyConstraint;
import org.schemaanalyst.schema.Constraint;
import org.schemaanalyst.schema.ConstraintVisitor;
import org.schemaanalyst.schema.NotNullConstraint;
import org.schemaanalyst.schema.PrimaryKeyConstraint;
import org.schemaanalyst.schema.UniqueConstraint;
import static org.schemaanalyst.sqlwriter.SQLWriter.writeColumnList;

public class ConstraintSQLWriter {
	
	protected CheckPredicateSQLWriter predicateSQLWriter;
	
	public void setPredicateSQLWriter(CheckPredicateSQLWriter predicateSQLWriter) {
		this.predicateSQLWriter = predicateSQLWriter;
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
		
		String sql = "";
		String name = constraint.getName();
		if (name != null) {
			sql = "CONSTRAINT " + name + " ";
		}		
		sql += (new ConstraintSQLWriterVisitor()).writeConstraint(constraint);
		return sql;
	}
	
	public String writeCheck(CheckConstraint check) {
		return "CHECK(" + predicateSQLWriter.writePredicate(check.getPredicate()) + ")";
	}
	
	public String writeForeignKey(ForeignKeyConstraint foreignKey) {
		return "FOREIGN KEY(" + writeColumnList(foreignKey.getColumns()) + ")" +
			   " REFERENCES " + foreignKey.getReferenceTable().getName() + 
		       "(" + SQLWriter.writeColumnList(foreignKey.getReferenceColumns()) + ")";				
	}
	
	public String writeNotNull(NotNullConstraint notNull) {
		return "NOT NULL";
	}
	
	public String writePrimaryKey(PrimaryKeyConstraint primaryKey) {
		return "PRIMARY KEY(" + writeColumnList(primaryKey.getColumns()) + ")";
	}
	
	public String writeUnique(UniqueConstraint unique) {
		return "UNIQUE(" + writeColumnList(unique.getColumns()) + ")";
	}
}