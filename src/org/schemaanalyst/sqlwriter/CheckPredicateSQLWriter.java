package org.schemaanalyst.sqlwriter;

import org.schemaanalyst.data.Value;
import org.schemaanalyst.schema.BetweenCheckPredicate;
import org.schemaanalyst.schema.InCheckPredicate;
import org.schemaanalyst.schema.CheckPredicate;
import org.schemaanalyst.schema.CheckPredicateVisitor;
import org.schemaanalyst.schema.RelationalCheckPredicate;

public class CheckPredicateSQLWriter {
	
	protected OperandSQLWriter operandSQLWriter;
	
	public void setOperandSQLWriter(OperandSQLWriter operandSQLWriter) {
		this.operandSQLWriter = operandSQLWriter;
	}
	
	public String writePredicate(CheckPredicate predicate) {
		
		class PredicateSQLWriterVisitor implements CheckPredicateVisitor {
			String sql;
			
			public String writePredicate(CheckPredicate predicate) {
				sql = "";
				predicate.accept(this);
				return sql;
			}
			
			public void visit(BetweenCheckPredicate predicate) {
				sql = writeBetweenPredicate(predicate);
			}

			public void visit(InCheckPredicate predicate) {
				sql = writeInPredicate(predicate);
			}

			public void visit(RelationalCheckPredicate predicate) {
				sql = writeRelationalPredicate(predicate);
			}
			
		}
		
		return (new PredicateSQLWriterVisitor()).writePredicate(predicate);
	}
	
	public String writeBetweenPredicate(BetweenCheckPredicate betweenPredicate) {
		return betweenPredicate.getColumn().getName() + 
			   " BETWEEN " + 
			   operandSQLWriter.writeOperand(betweenPredicate.getLower()) + 
			   " AND "+ 
			   operandSQLWriter.writeOperand(betweenPredicate.getUpper());
	}	
	
	public String writeInPredicate(InCheckPredicate inPredicate) {
		StringBuilder sb = new StringBuilder();
		sb.append(inPredicate.getColumn().getName());
		
		boolean first = true;
		sb.append(" IN (");
		for (Value value : inPredicate.getValues()) {
			if (first) first = false;
			else sb.append(", ");
			sb.append(operandSQLWriter.writeOperand(value));
		}
		sb.append(")");
		return sb.toString();
	}
	
	public String writeRelationalPredicate(RelationalCheckPredicate relationalPredicate) {
		return operandSQLWriter.writeOperand(relationalPredicate.getLHS()) + " " +
               relationalPredicate.getOperator() + " " + 
               operandSQLWriter.writeOperand(relationalPredicate.getRHS());
	}
}
