package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.CharDataType;

/*
 * NistDML183NotNulls schema.
 * Java code originally generated: 2013/07/11 14:10:12
 *
 */

@SuppressWarnings("serial")
public class NistDML183NotNulls extends Schema {

	public NistDML183NotNulls() {
		super("NistDML183NotNulls");

		Table tableT = this.createTable("T");
		tableT.addColumn("A", new CharDataType());
		tableT.addColumn("B", new CharDataType());
		tableT.addColumn("C", new CharDataType());
		tableT.addNotNullConstraint(tableT.getColumn("A"));
		tableT.addNotNullConstraint(tableT.getColumn("B"));
		tableT.addNotNullConstraint(tableT.getColumn("C"));
		tableT.addUniqueConstraint("UniqueOnColsAandB", tableT.getColumn("A"), tableT.getColumn("B"));

		Table tableS = this.createTable("S");
		tableS.addColumn("X", new CharDataType());
		tableS.addColumn("Y", new CharDataType());
		tableS.addColumn("Z", new CharDataType());
		tableS.addForeignKeyConstraint("RefToColsAandB", tableS.getColumn("X"), tableS.getColumn("Y"), tableT, tableT.getColumn("A"), tableT.getColumn("B"));
		tableS.addNotNullConstraint(tableS.getColumn("X"));
		tableS.addNotNullConstraint(tableS.getColumn("Y"));
		tableS.addNotNullConstraint(tableS.getColumn("Z"));
	}
}

