package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * Cloc schema.
 * Java code originally generated: 2013/07/11 14:07:02
 *
 */

@SuppressWarnings("serial")
public class Cloc extends Schema {

	public Cloc() {
		super("Cloc");

		Table tableMetadata = this.createTable("metadata");
		tableMetadata.addColumn("timestamp", new VarCharDataType(50));
		tableMetadata.addColumn("Project", new VarCharDataType(50));
		tableMetadata.addColumn("elapsed_s", new IntDataType());

		Table tableT = this.createTable("t");
		tableT.addColumn("Project", new VarCharDataType(50));
		tableT.addColumn("Language", new VarCharDataType(50));
		tableT.addColumn("File", new VarCharDataType(50));
		tableT.addColumn("nBlank", new IntDataType());
		tableT.addColumn("nComment", new IntDataType());
		tableT.addColumn("nCode", new IntDataType());
		tableT.addColumn("nScaled", new IntDataType());
	}
}

