package org.schemaanalyst.sqlwriter;

import org.schemaanalyst.schema.attribute.Attribute;
import org.schemaanalyst.schema.attribute.AttributeVisitor;
import org.schemaanalyst.schema.attribute.AutoIncrementAttribute;
import org.schemaanalyst.schema.attribute.DefaultAttribute;
import org.schemaanalyst.schema.attribute.IdentityAttribute;

public class AttributeSQLWriter {

	protected OperandSQLWriter operandSQLWriter;

	public void setOperandSQLWriter(OperandSQLWriter operandSQLWriter) {
		this.operandSQLWriter = operandSQLWriter;
	}
	
	public String writeAttribute(Attribute attribute) {
		
		class AttributeSQLWriterVisitor implements AttributeVisitor {

			String sql;
			
			public String writeAttribute(Attribute attribute) {
				sql = "";
				attribute.accept(this);
				return sql;
			}
			
			public void visit(AutoIncrementAttribute attribute) {
				sql = writeAutoIncrementAttribute(attribute);
			}

			public void visit(DefaultAttribute attribute) {
				sql = writeDefaultAttribute(attribute);
			}

			public void visit(IdentityAttribute attribute) {
				sql = writeIdentityAttribute(attribute);
			}
			
		}
		
		return (new AttributeSQLWriterVisitor()).writeAttribute(attribute);
	}
	
	public String writeAutoIncrementAttribute(AutoIncrementAttribute autoIncrement) {
		return "AUTOINCREMENT";
	}

	public String writeDefaultAttribute(DefaultAttribute def) {
		return "DEFAULT " + operandSQLWriter.writeOperand(def.getValue());
	}
	
	public String writeIdentityAttribute(IdentityAttribute identity) {
		return "IDENTITY(" + identity.getSeed() + "," + identity.getIncrement() + ")";
	}
	
}
