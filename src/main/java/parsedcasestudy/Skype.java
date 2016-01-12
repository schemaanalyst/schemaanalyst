package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * Skype schema.
 * Java code originally generated: 2014/04/22 20:45:15
 *
 */

@SuppressWarnings("serial")
public class Skype extends Schema {

	public Skype() {
		super("Skype");

		Table tableBupdate = this.createTable("bupdate");
		tableBupdate.createColumn("uri", new VarCharDataType(256));
		tableBupdate.createColumn("type", new VarCharDataType(256));
		tableBupdate.createColumn("meta", new VarCharDataType(256));
		tableBupdate.createColumn("prio", new IntDataType());
		tableBupdate.createColumn("id", new IntDataType());
		tableBupdate.createColumn("body", new VarCharDataType(256));
		tableBupdate.createColumn("terms", new VarCharDataType(256));

		Table tableEvents = this.createTable("events");
		tableEvents.createColumn("id", new IntDataType());
		tableEvents.createColumn("event", new IntDataType());
		tableEvents.createColumn("event_time", new IntDataType());

		Table tableInstall = this.createTable("install");
		tableInstall.createColumn("uri", new VarCharDataType(256));
		tableInstall.createColumn("type", new VarCharDataType(256));
		tableInstall.createColumn("meta", new VarCharDataType(256));
		tableInstall.createColumn("prio", new IntDataType());
		tableInstall.createColumn("id", new IntDataType());
		tableInstall.createColumn("body", new VarCharDataType(256));
		tableInstall.createColumn("terms", new VarCharDataType(256));

		Table tableItemkeys = this.createTable("itemkeys");
		tableItemkeys.createColumn("id", new IntDataType());
		tableItemkeys.createColumn("keys", new VarCharDataType(256));

		Table tableReported = this.createTable("reported");
		tableReported.createColumn("id", new IntDataType());
		tableReported.createColumn("exposed_cnt", new IntDataType());
		tableReported.createColumn("exposed_time", new IntDataType());
		tableReported.createColumn("exec_cnt", new IntDataType());
		tableReported.createColumn("close_cnt", new IntDataType());

		Table tableStats = this.createTable("stats");
		tableStats.createColumn("id", new IntDataType());
		tableStats.createColumn("exposed_cnt", new IntDataType());
		tableStats.createColumn("exposed_time", new IntDataType());
		tableStats.createColumn("exec_cnt", new IntDataType());
		tableStats.createColumn("close_cnt", new IntDataType());
		tableStats.createColumn("last_expose_start", new IntDataType());
		tableStats.createColumn("exposing_now", new IntDataType());
	}
}

