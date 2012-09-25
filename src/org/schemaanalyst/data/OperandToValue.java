package org.schemaanalyst.data;

import org.schemaanalyst.schema.Column;
import org.schemaanalyst.schema.Operand;
import org.schemaanalyst.schema.OperandVisitor;

public class OperandToValue {

	public static Value convert(Operand operand, Data data, int row) {
		
		class OperandToValueConverter implements OperandVisitor {
			Value value;
			Data data;
			int row;
			
			Value toValue(Operand operand, Data data, int row) {
				value = null;
				this.data = data;
				this.row = row;
				operand.accept(this);
				return value;
			}

			public void visit(Column column) {
				value = data.getCell(column.getTable(), column, row).getValue();
			}

			public void visit(Value value) {
				this.value = value;
			}
		}		
		
		return (new OperandToValueConverter()).toValue(operand, data, row);
	}
}
