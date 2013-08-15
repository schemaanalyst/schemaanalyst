package parsedcasestudy;

import java.util.Arrays;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

/*
 * NistDML183IntsNotNulls schema.
 * Java code originally generated: 2013/08/15 23:00:27
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
		tableT.createNotNullConstraint(tableT.getColumn("A"));
		tableT.createNotNullConstraint(tableT.getColumn("B"));
		tableT.createNotNullConstraint(tableT.getColumn("C"));
		tableT.createUniqueConstraint("UniqueOnColsAandB", tableT.getColumn("A"), tableT.getColumn("B"));

		Table tableS = this.createTable("S");
		tableS.createColumn("X", new IntDataType());
		tableS.createColumn("Y", new IntDataType());
		tableS.createColumn("Z", new IntDataType());
		tableS.createForeignKeyConstraint("RefToColsAandB", Arrays.asList(tableS.getColumn("X"), tableS.getColumn("Y")), tableT, Arrays.asList(tableT.getColumn("A"), tableT.getColumn("B")));
		tableS.createNotNullConstraint(tableS.getColumn("X"));
		tableS.createNotNullConstraint(tableS.getColumn("Y"));
		tableS.createNotNullConstraint(tableS.getColumn("Z"));
	}
}

