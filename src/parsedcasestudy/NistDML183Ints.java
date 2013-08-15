package parsedcasestudy;

import java.util.Arrays;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

/*
 * NistDML183Ints schema.
 * Java code originally generated: 2013/08/15 10:52:03
 *
 */

@SuppressWarnings("serial")
public class NistDML183Ints extends Schema {

	public NistDML183Ints() {
		super("NistDML183Ints");

		Table tableT = this.createTable("T");
		tableT.createColumn("A", new IntDataType());
		tableT.createColumn("B", new IntDataType());
		tableT.createColumn("C", new IntDataType());
		tableT.createUniqueConstraint("UniqueOnColsAandB", tableT.getColumn("A"), tableT.getColumn("B"));

		Table tableS = this.createTable("S");
		tableS.createColumn("X", new IntDataType());
		tableS.createColumn("Y", new IntDataType());
		tableS.createColumn("Z", new IntDataType());
		tableS.createForeignKeyConstraint("RefToColsAandB", Arrays.asList(tableS.getColumn("X"), tableS.getColumn("Y")), tableT, Arrays.asList(tableS.getColumn("A"), tableS.getColumn("B")));
	}
}

