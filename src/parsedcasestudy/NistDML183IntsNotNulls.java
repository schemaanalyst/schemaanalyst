package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

import java.util.Arrays;

/*
 * NistDML183IntsNotNulls schema.
 * Java code originally generated: 2013/08/17 00:30:50
 *
 */

@SuppressWarnings("serial")
public class NistDML183IntsNotNulls extends Schema {

	public NistDML183IntsNotNulls() {
		super("NistDML183IntsNotNulls");

		Table tableT = this.createTable("T");
		tableT.createColumn("A", new IntDataType());
		tableT.createColumn("B", new IntDataType());
		tableT.createColumn("C", new IntDataType());
		this.createNotNullConstraint(tableT, tableT.getColumn("A"));
		this.createNotNullConstraint(tableT, tableT.getColumn("B"));
		this.createNotNullConstraint(tableT, tableT.getColumn("C"));
		this.createUniqueConstraint("UniqueOnColsAandB", tableT, tableT.getColumn("A"), tableT.getColumn("B"));

		Table tableS = this.createTable("S");
		tableS.createColumn("X", new IntDataType());
		tableS.createColumn("Y", new IntDataType());
		tableS.createColumn("Z", new IntDataType());
		this.createForeignKeyConstraint("RefToColsAandB", tableS, Arrays.asList(tableS.getColumn("X"), tableS.getColumn("Y")), tableT, Arrays.asList(tableT.getColumn("A"), tableT.getColumn("B")));
		this.createNotNullConstraint(tableS, tableS.getColumn("X"));
		this.createNotNullConstraint(tableS, tableS.getColumn("Y"));
		this.createNotNullConstraint(tableS, tableS.getColumn("Z"));
	}
}

