package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

import java.util.Arrays;

/*
 * NistDML183VarcharsNotNulls schema.
 * Java code originally generated: 2013/08/17 00:30:54
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
		this.createNotNullConstraint(tableT, tableT.getColumn("A"));
		this.createNotNullConstraint(tableT, tableT.getColumn("B"));
		this.createNotNullConstraint(tableT, tableT.getColumn("C"));
		this.createUniqueConstraint("UniqueOnColsAandB", tableT, tableT.getColumn("A"), tableT.getColumn("B"));

		Table tableS = this.createTable("S");
		tableS.createColumn("X", new VarCharDataType(10));
		tableS.createColumn("Y", new VarCharDataType(10));
		tableS.createColumn("Z", new VarCharDataType(10));
		this.createForeignKeyConstraint("RefToColsAandB", tableS, Arrays.asList(tableS.getColumn("X"), tableS.getColumn("Y")), tableT, Arrays.asList(tableT.getColumn("A"), tableT.getColumn("B")));
		this.createNotNullConstraint(tableS, tableS.getColumn("X"));
		this.createNotNullConstraint(tableS, tableS.getColumn("Y"));
		this.createNotNullConstraint(tableS, tableS.getColumn("Z"));
	}
}

