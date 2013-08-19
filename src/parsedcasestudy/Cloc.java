package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * Cloc schema.
 * Java code originally generated: 2013/08/17 00:30:28
 *
 */

@SuppressWarnings("serial")
public class Cloc extends Schema {

	public Cloc() {
		super("Cloc");

		Table tableMetadata = this.createTable("metadata");
		tableMetadata.createColumn("timestamp", new VarCharDataType(50));
		tableMetadata.createColumn("Project", new VarCharDataType(50));
		tableMetadata.createColumn("elapsed_s", new IntDataType());

		Table tableT = this.createTable("t");
		tableT.createColumn("Project", new VarCharDataType(50));
		tableT.createColumn("Language", new VarCharDataType(50));
		tableT.createColumn("File", new VarCharDataType(50));
		tableT.createColumn("nBlank", new IntDataType());
		tableT.createColumn("nComment", new IntDataType());
		tableT.createColumn("nCode", new IntDataType());
		tableT.createColumn("nScaled", new IntDataType());
	}
}

