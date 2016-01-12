package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.SingleCharDataType;

import java.util.Arrays;

/*
 * NistDML183NotNulls schema.
 * Java code originally generated: 2013/08/17 00:30:52
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
		this.createNotNullConstraint(tableT, tableT.getColumn("A"));
		this.createNotNullConstraint(tableT, tableT.getColumn("B"));
		this.createNotNullConstraint(tableT, tableT.getColumn("C"));
		this.createUniqueConstraint("UniqueOnColsAandB", tableT, tableT.getColumn("A"), tableT.getColumn("B"));

		Table tableS = this.createTable("S");
		tableS.createColumn("X", new SingleCharDataType());
		tableS.createColumn("Y", new SingleCharDataType());
		tableS.createColumn("Z", new SingleCharDataType());
		this.createForeignKeyConstraint("RefToColsAandB", tableS, Arrays.asList(tableS.getColumn("X"), tableS.getColumn("Y")), tableT, Arrays.asList(tableT.getColumn("A"), tableT.getColumn("B")));
		this.createNotNullConstraint(tableS, tableS.getColumn("X"));
		this.createNotNullConstraint(tableS, tableS.getColumn("Y"));
		this.createNotNullConstraint(tableS, tableS.getColumn("Z"));
	}
}

