package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

/*
 * NistDML183Ints schema.
 * Java code originally generated: 2013/07/11 14:10:00
 *
 */

@SuppressWarnings("serial")
public class NistDML183Ints extends Schema {

	public NistDML183Ints() {
		super("NistDML183Ints");

		Table tableT = this.createTable("T");
		tableT.addColumn("A", new IntDataType());
		tableT.addColumn("B", new IntDataType());
		tableT.addColumn("C", new IntDataType());
		tableT.addUniqueConstraint("UniqueOnColsAandB", tableT.getColumn("A"), tableT.getColumn("B"));

		Table tableS = this.createTable("S");
		tableS.addColumn("X", new IntDataType());
		tableS.addColumn("Y", new IntDataType());
		tableS.addColumn("Z", new IntDataType());
		tableS.addForeignKeyConstraint("RefToColsAandB", tableS.getColumn("X"), tableS.getColumn("Y"), tableT, tableT.getColumn("A"), tableT.getColumn("B"));
	}
}

