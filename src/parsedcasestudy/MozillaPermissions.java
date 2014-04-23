package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TextDataType;

/*
 * MozillaPermissions schema.
 * Java code originally generated: 2014/04/16 11:32:39
 *
 */

@SuppressWarnings("serial")
public class MozillaPermissions extends Schema {

	public MozillaPermissions() {
		super("MozillaPermissions");

		Table tableMozHosts = this.createTable("moz_hosts");
		tableMozHosts.createColumn("id", new IntDataType());
		tableMozHosts.createColumn("host", new TextDataType());
		tableMozHosts.createColumn("type", new TextDataType());
		tableMozHosts.createColumn("permission", new IntDataType());
		tableMozHosts.createColumn("expireType", new IntDataType());
		tableMozHosts.createColumn("expireTime", new IntDataType());
		tableMozHosts.createColumn("appId", new IntDataType());
		tableMozHosts.createColumn("isInBrowserElement", new IntDataType());
		this.createPrimaryKeyConstraint("null", tableMozHosts, tableMozHosts.getColumn("id"));
	}
}

