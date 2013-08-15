package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.SmallIntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TimestampDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * JWhoisServer schema.
 * Java code originally generated: 2013/08/15 10:51:54
 *
 */

@SuppressWarnings("serial")
public class JWhoisServer extends Schema {

	public JWhoisServer() {
		super("JWhoisServer");

		Table tableDomain = this.createTable("domain");
		tableDomain.createColumn("domain_key", new IntDataType());
		tableDomain.createColumn("domain", new VarCharDataType(255));
		tableDomain.createColumn("registered_date", new TimestampDataType());
		tableDomain.createColumn("registerexpire_date", new TimestampDataType());
		tableDomain.createColumn("changed", new TimestampDataType());
		tableDomain.createColumn("remarks", new VarCharDataType(255));
		tableDomain.createColumn("holder", new IntDataType());
		tableDomain.createColumn("admin_c", new IntDataType());
		tableDomain.createColumn("tech_c", new IntDataType());
		tableDomain.createColumn("zone_c", new IntDataType());
		tableDomain.createColumn("mntnr_fkey", new IntDataType());
		tableDomain.createColumn("publicviewabledata", new SmallIntDataType());
		tableDomain.createColumn("disabled", new SmallIntDataType());
		tableDomain.createPrimaryKeyConstraint(tableDomain.getColumn("domain_key"));
		tableDomain.createNotNullConstraint(tableDomain.getColumn("domain_key"));
		tableDomain.createNotNullConstraint(tableDomain.getColumn("domain"));
		tableDomain.createNotNullConstraint(tableDomain.getColumn("registered_date"));
		tableDomain.createNotNullConstraint(tableDomain.getColumn("registerexpire_date"));
		tableDomain.createNotNullConstraint(tableDomain.getColumn("changed"));
		tableDomain.createNotNullConstraint(tableDomain.getColumn("holder"));
		tableDomain.createNotNullConstraint(tableDomain.getColumn("admin_c"));
		tableDomain.createNotNullConstraint(tableDomain.getColumn("tech_c"));
		tableDomain.createNotNullConstraint(tableDomain.getColumn("zone_c"));
		tableDomain.createNotNullConstraint(tableDomain.getColumn("mntnr_fkey"));
		tableDomain.createNotNullConstraint(tableDomain.getColumn("publicviewabledata"));
		tableDomain.createNotNullConstraint(tableDomain.getColumn("disabled"));

		Table tableMntnr = this.createTable("mntnr");
		tableMntnr.createColumn("mntnr_key", new IntDataType());
		tableMntnr.createColumn("login", new VarCharDataType(255));
		tableMntnr.createColumn("password", new VarCharDataType(255));
		tableMntnr.createColumn("name", new VarCharDataType(255));
		tableMntnr.createColumn("address", new VarCharDataType(255));
		tableMntnr.createColumn("pcode", new VarCharDataType(20));
		tableMntnr.createColumn("city", new VarCharDataType(255));
		tableMntnr.createColumn("country_fkey", new IntDataType());
		tableMntnr.createColumn("phone", new VarCharDataType(100));
		tableMntnr.createColumn("fax", new VarCharDataType(100));
		tableMntnr.createColumn("email", new VarCharDataType(255));
		tableMntnr.createColumn("remarks", new VarCharDataType(255));
		tableMntnr.createColumn("changed", new TimestampDataType());
		tableMntnr.createColumn("disabled", new SmallIntDataType());
		tableMntnr.createPrimaryKeyConstraint(tableMntnr.getColumn("mntnr_key"));
		tableMntnr.createNotNullConstraint(tableMntnr.getColumn("mntnr_key"));
		tableMntnr.createNotNullConstraint(tableMntnr.getColumn("login"));
		tableMntnr.createNotNullConstraint(tableMntnr.getColumn("password"));
		tableMntnr.createNotNullConstraint(tableMntnr.getColumn("name"));
		tableMntnr.createNotNullConstraint(tableMntnr.getColumn("address"));
		tableMntnr.createNotNullConstraint(tableMntnr.getColumn("pcode"));
		tableMntnr.createNotNullConstraint(tableMntnr.getColumn("city"));
		tableMntnr.createNotNullConstraint(tableMntnr.getColumn("country_fkey"));
		tableMntnr.createNotNullConstraint(tableMntnr.getColumn("phone"));
		tableMntnr.createNotNullConstraint(tableMntnr.getColumn("email"));
		tableMntnr.createNotNullConstraint(tableMntnr.getColumn("changed"));
		tableMntnr.createNotNullConstraint(tableMntnr.getColumn("disabled"));

		Table tablePerson = this.createTable("person");
		tablePerson.createColumn("person_key", new IntDataType());
		tablePerson.createColumn("type_fkey", new IntDataType());
		tablePerson.createColumn("name", new VarCharDataType(255));
		tablePerson.createColumn("address", new VarCharDataType(255));
		tablePerson.createColumn("pcode", new VarCharDataType(20));
		tablePerson.createColumn("city", new VarCharDataType(255));
		tablePerson.createColumn("country_fkey", new IntDataType());
		tablePerson.createColumn("phone", new VarCharDataType(100));
		tablePerson.createColumn("fax", new VarCharDataType(100));
		tablePerson.createColumn("email", new VarCharDataType(255));
		tablePerson.createColumn("remarks", new VarCharDataType(255));
		tablePerson.createColumn("changed", new TimestampDataType());
		tablePerson.createColumn("mntnr_fkey", new IntDataType());
		tablePerson.createColumn("disabled", new SmallIntDataType());
		tablePerson.createPrimaryKeyConstraint(tablePerson.getColumn("person_key"));
		tablePerson.createNotNullConstraint(tablePerson.getColumn("person_key"));
		tablePerson.createNotNullConstraint(tablePerson.getColumn("type_fkey"));
		tablePerson.createNotNullConstraint(tablePerson.getColumn("name"));
		tablePerson.createNotNullConstraint(tablePerson.getColumn("address"));
		tablePerson.createNotNullConstraint(tablePerson.getColumn("pcode"));
		tablePerson.createNotNullConstraint(tablePerson.getColumn("city"));
		tablePerson.createNotNullConstraint(tablePerson.getColumn("country_fkey"));
		tablePerson.createNotNullConstraint(tablePerson.getColumn("phone"));
		tablePerson.createNotNullConstraint(tablePerson.getColumn("email"));
		tablePerson.createNotNullConstraint(tablePerson.getColumn("changed"));
		tablePerson.createNotNullConstraint(tablePerson.getColumn("mntnr_fkey"));
		tablePerson.createNotNullConstraint(tablePerson.getColumn("disabled"));

		Table tableType = this.createTable("type");
		tableType.createColumn("type_key", new IntDataType());
		tableType.createColumn("type", new VarCharDataType(100));
		tableType.createPrimaryKeyConstraint(tableType.getColumn("type_key"));
		tableType.createNotNullConstraint(tableType.getColumn("type_key"));
		tableType.createNotNullConstraint(tableType.getColumn("type"));

		Table tableNameserver = this.createTable("nameserver");
		tableNameserver.createColumn("nameserver_key", new IntDataType());
		tableNameserver.createColumn("nameserver", new VarCharDataType(255));
		tableNameserver.createColumn("domain_fkey", new IntDataType());
		tableNameserver.createPrimaryKeyConstraint(tableNameserver.getColumn("nameserver_key"));
		tableNameserver.createNotNullConstraint(tableNameserver.getColumn("nameserver_key"));
		tableNameserver.createNotNullConstraint(tableNameserver.getColumn("nameserver"));
		tableNameserver.createNotNullConstraint(tableNameserver.getColumn("domain_fkey"));

		Table tableCountry = this.createTable("country");
		tableCountry.createColumn("country_key", new IntDataType());
		tableCountry.createColumn("short", new VarCharDataType(2));
		tableCountry.createColumn("country", new VarCharDataType(255));
		tableCountry.createPrimaryKeyConstraint(tableCountry.getColumn("country_key"));
		tableCountry.createNotNullConstraint(tableCountry.getColumn("country_key"));
		tableCountry.createNotNullConstraint(tableCountry.getColumn("short"));
		tableCountry.createNotNullConstraint(tableCountry.getColumn("country"));
	}
}

