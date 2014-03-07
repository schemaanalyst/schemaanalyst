package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;

import java.util.Arrays;

/*
 * NistDML183Ints schema.
 * Java code originally generated: 2013/08/17 00:30:49
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
		this.createUniqueConstraint("UniqueOnColsAandB", tableT, tableT.getColumn("A"), tableT.getColumn("B"));

		Table tableS = this.createTable("S");
		tableS.createColumn("X", new IntDataType());
		tableS.createColumn("Y", new IntDataType());
		tableS.createColumn("Z", new IntDataType());
		this.createForeignKeyConstraint("RefToColsAandB", tableS, Arrays.asList(tableS.getColumn("X"), tableS.getColumn("Y")), tableT, Arrays.asList(tableT.getColumn("A"), tableT.getColumn("B")));
	}
}

