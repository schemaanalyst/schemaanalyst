package org.schemaanalyst.sqlwriter;

import org.schemaanalyst.data.Value;
import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.Operand;
import org.schemaanalyst.schema.OperandVisitor;

public class OperandSQLWriter {

	protected ValueSQLWriter valueSQLWriter;
	
	public void setValueSQLWriter(ValueSQLWriter valueSQLWriter) {
		this.valueSQLWriter = valueSQLWriter;
	}
	
	public String writeOperand(Operand operand) {
		
		class OperandSQLWriterVisitor implements OperandVisitor {
			String sql;
			
			public String writeOperand(Operand operand) {
				sql = "";
				operand.accept(this);
				return sql;
			}

			public void visit(Column column) {
				sql = writeColumn(column);
			}

			public void visit(Value value) {
				sql = writeValue(value);
			}
		}
		
		if (operand == null) {
			return valueSQLWriter.writeNullValue();
		} else {	
			return (new OperandSQLWriterVisitor()).writeOperand(operand);
		}
	}
		
	public String writeColumn(Column operand) {
		return operand.getName();
	}

	public String writeValue(Value value) {
		return valueSQLWriter.writeValue(value);
	}	
}
