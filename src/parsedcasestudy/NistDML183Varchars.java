package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

import java.util.Arrays;

/*
 * NistDML183Varchars schema.
 * Java code originally generated: 2013/08/17 00:30:53
 *
 */

@SuppressWarnings("serial")
public class NistDML183Varchars extends Schema {

	public NistDML183Varchars() {
		super("NistDML183Varchars");

		Table tableT = this.createTable("T");
		tableT.createColumn("A", new VarCharDataType(1));
		tableT.createColumn("B", new VarCharDataType(1));
		tableT.createColumn("C", new VarCharDataType(1));
		this.createUniqueConstraint("UniqueOnColsAandB", tableT, tableT.getColumn("A"), tableT.getColumn("B"));

		Table tableS = this.createTable("S");
		tableS.createColumn("X", new VarCharDataType(1));
		tableS.createColumn("Y", new VarCharDataType(1));
		tableS.createColumn("Z", new VarCharDataType(1));
		this.createForeignKeyConstraint("RefToColsAandB", tableS, Arrays.asList(tableS.getColumn("X"), tableS.getColumn("Y")), tableT, Arrays.asList(tableT.getColumn("A"), tableT.getColumn("B")));
	}
}

