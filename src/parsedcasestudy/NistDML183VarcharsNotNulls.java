package parsedcasestudy;

import java.util.Arrays;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * NistDML183VarcharsNotNulls schema.
 * Java code originally generated: 2013/08/15 23:00:31
 *
 */

@SuppressWarnings("serial")
public class NistDML183VarcharsNotNulls extends Schema {

	public NistDML183VarcharsNotNulls() {
		super("NistDML183VarcharsNotNulls");

		Table tableT = this.createTable("T");
		tableT.createColumn("A", new VarCharDataType(10));
		tableT.createColumn("B", new VarCharDataType(10));
		tableT.createColumn("C", new VarCharDataType(10));
		tableT.createNotNullConstraint(tableT.getColumn("A"));
		tableT.createNotNullConstraint(tableT.getColumn("B"));
		tableT.createNotNullConstraint(tableT.getColumn("C"));
		tableT.createUniqueConstraint("UniqueOnColsAandB", tableT.getColumn("A"), tableT.getColumn("B"));

		Table tableS = this.createTable("S");
		tableS.createColumn("X", new VarCharDataType(10));
		tableS.createColumn("Y", new VarCharDataType(10));
		tableS.createColumn("Z", new VarCharDataType(10));
		tableS.createForeignKeyConstraint("RefToColsAandB", Arrays.asList(tableS.getColumn("X"), tableS.getColumn("Y")), tableT, Arrays.asList(tableT.getColumn("A"), tableT.getColumn("B")));
		tableS.createNotNullConstraint(tableS.getColumn("X"));
		tableS.createNotNullConstraint(tableS.getColumn("Y"));
		tableS.createNotNullConstraint(tableS.getColumn("Z"));
	}
}

