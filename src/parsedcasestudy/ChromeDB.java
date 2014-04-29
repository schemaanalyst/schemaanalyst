package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * ChromeDB schema.
 * Java code originally generated: 2014/04/22 20:11:48
 *
 */

@SuppressWarnings("serial")
public class ChromeDB extends Schema {

	public ChromeDB() {
		super("ChromeDB");

		Table tableDatabases = this.createTable("Databases");
		tableDatabases.createColumn("id", new IntDataType());
		tableDatabases.createColumn("origin", new VarCharDataType(256));
		tableDatabases.createColumn("name", new VarCharDataType(256));
		tableDatabases.createColumn("description", new VarCharDataType(256));
		tableDatabases.createColumn("estimated_size", new IntDataType());
		this.createPrimaryKeyConstraint(tableDatabases, tableDatabases.getColumn("id"));
		this.createNotNullConstraint(tableDatabases, tableDatabases.getColumn("origin"));
		this.createNotNullConstraint(tableDatabases, tableDatabases.getColumn("name"));
		this.createNotNullConstraint(tableDatabases, tableDatabases.getColumn("description"));
		this.createNotNullConstraint(tableDatabases, tableDatabases.getColumn("estimated_size"));
		this.createUniqueConstraint("unique_index", tableDatabases, tableDatabases.getColumn("origin"), tableDatabases.getColumn("name"));

		Table tableMeta = this.createTable("meta");
		tableMeta.createColumn("key", new VarCharDataType(256));
		tableMeta.createColumn("value", new VarCharDataType(256));
		this.createPrimaryKeyConstraint(tableMeta, tableMeta.getColumn("key"));
		this.createNotNullConstraint(tableMeta, tableMeta.getColumn("key"));
		this.createUniqueConstraint(tableMeta, tableMeta.getColumn("key"));
	}
}

