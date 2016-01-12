package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.SingleCharDataType;

import java.util.Arrays;

/*
 * NistDML183 schema.
 * Java code originally generated: 2013/08/17 00:30:48
 *
 */

@SuppressWarnings("serial")
public class NistDML183 extends Schema {

	public NistDML183() {
		super("NistDML183");

		Table tableT = this.createTable("T");
		tableT.createColumn("A", new SingleCharDataType());
		tableT.createColumn("B", new SingleCharDataType());
		tableT.createColumn("C", new SingleCharDataType());
		this.createUniqueConstraint("UniqueOnColsAandB", tableT, tableT.getColumn("A"), tableT.getColumn("B"));

		Table tableS = this.createTable("S");
		tableS.createColumn("X", new SingleCharDataType());
		tableS.createColumn("Y", new SingleCharDataType());
		tableS.createColumn("Z", new SingleCharDataType());
		this.createForeignKeyConstraint("RefToColsAandB", tableS, Arrays.asList(tableS.getColumn("X"), tableS.getColumn("Y")), tableT, Arrays.asList(tableT.getColumn("A"), tableT.getColumn("B")));
	}
}

