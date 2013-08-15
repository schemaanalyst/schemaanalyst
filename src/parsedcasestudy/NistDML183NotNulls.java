package parsedcasestudy;

import java.util.Arrays;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.SingleCharDataType;

/*
 * NistDML183NotNulls schema.
 * Java code originally generated: 2013/08/15 23:00:28
 *
 */

@SuppressWarnings("serial")
public class NistDML183NotNulls extends Schema {

	public NistDML183NotNulls() {
		super("NistDML183NotNulls");

		Table tableT = this.createTable("T");
		tableT.createColumn("A", new SingleCharDataType());
		tableT.createColumn("B", new SingleCharDataType());
		tableT.createColumn("C", new SingleCharDataType());
		tableT.createNotNullConstraint(tableT.getColumn("A"));
		tableT.createNotNullConstraint(tableT.getColumn("B"));
		tableT.createNotNullConstraint(tableT.getColumn("C"));
		tableT.createUniqueConstraint("UniqueOnColsAandB", tableT.getColumn("A"), tableT.getColumn("B"));

		Table tableS = this.createTable("S");
		tableS.createColumn("X", new SingleCharDataType());
		tableS.createColumn("Y", new SingleCharDataType());
		tableS.createColumn("Z", new SingleCharDataType());
		tableS.createForeignKeyConstraint("RefToColsAandB", Arrays.asList(tableS.getColumn("X"), tableS.getColumn("Y")), tableT, Arrays.asList(tableT.getColumn("A"), tableT.getColumn("B")));
		tableS.createNotNullConstraint(tableS.getColumn("X"));
		tableS.createNotNullConstraint(tableS.getColumn("Y"));
		tableS.createNotNullConstraint(tableS.getColumn("Z"));
	}
}

