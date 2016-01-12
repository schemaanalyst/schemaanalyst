package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.SmallIntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TimestampDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * JWhoisServer schema.
 * Java code originally generated: 2013/08/17 00:30:42
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
		this.createPrimaryKeyConstraint(tableDomain, tableDomain.getColumn("domain_key"));
		this.createNotNullConstraint(tableDomain, tableDomain.getColumn("domain_key"));
		this.createNotNullConstraint(tableDomain, tableDomain.getColumn("domain"));
		this.createNotNullConstraint(tableDomain, tableDomain.getColumn("registered_date"));
		this.createNotNullConstraint(tableDomain, tableDomain.getColumn("registerexpire_date"));
		this.createNotNullConstraint(tableDomain, tableDomain.getColumn("changed"));
		this.createNotNullConstraint(tableDomain, tableDomain.getColumn("holder"));
		this.createNotNullConstraint(tableDomain, tableDomain.getColumn("admin_c"));
		this.createNotNullConstraint(tableDomain, tableDomain.getColumn("tech_c"));
		this.createNotNullConstraint(tableDomain, tableDomain.getColumn("zone_c"));
		this.createNotNullConstraint(tableDomain, tableDomain.getColumn("mntnr_fkey"));
		this.createNotNullConstraint(tableDomain, tableDomain.getColumn("publicviewabledata"));
		this.createNotNullConstraint(tableDomain, tableDomain.getColumn("disabled"));

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
		this.createPrimaryKeyConstraint(tableMntnr, tableMntnr.getColumn("mntnr_key"));
		this.createNotNullConstraint(tableMntnr, tableMntnr.getColumn("mntnr_key"));
		this.createNotNullConstraint(tableMntnr, tableMntnr.getColumn("login"));
		this.createNotNullConstraint(tableMntnr, tableMntnr.getColumn("password"));
		this.createNotNullConstraint(tableMntnr, tableMntnr.getColumn("name"));
		this.createNotNullConstraint(tableMntnr, tableMntnr.getColumn("address"));
		this.createNotNullConstraint(tableMntnr, tableMntnr.getColumn("pcode"));
		this.createNotNullConstraint(tableMntnr, tableMntnr.getColumn("city"));
		this.createNotNullConstraint(tableMntnr, tableMntnr.getColumn("country_fkey"));
		this.createNotNullConstraint(tableMntnr, tableMntnr.getColumn("phone"));
		this.createNotNullConstraint(tableMntnr, tableMntnr.getColumn("email"));
		this.createNotNullConstraint(tableMntnr, tableMntnr.getColumn("changed"));
		this.createNotNullConstraint(tableMntnr, tableMntnr.getColumn("disabled"));

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
		this.createPrimaryKeyConstraint(tablePerson, tablePerson.getColumn("person_key"));
		this.createNotNullConstraint(tablePerson, tablePerson.getColumn("person_key"));
		this.createNotNullConstraint(tablePerson, tablePerson.getColumn("type_fkey"));
		this.createNotNullConstraint(tablePerson, tablePerson.getColumn("name"));
		this.createNotNullConstraint(tablePerson, tablePerson.getColumn("address"));
		this.createNotNullConstraint(tablePerson, tablePerson.getColumn("pcode"));
		this.createNotNullConstraint(tablePerson, tablePerson.getColumn("city"));
		this.createNotNullConstraint(tablePerson, tablePerson.getColumn("country_fkey"));
		this.createNotNullConstraint(tablePerson, tablePerson.getColumn("phone"));
		this.createNotNullConstraint(tablePerson, tablePerson.getColumn("email"));
		this.createNotNullConstraint(tablePerson, tablePerson.getColumn("changed"));
		this.createNotNullConstraint(tablePerson, tablePerson.getColumn("mntnr_fkey"));
		this.createNotNullConstraint(tablePerson, tablePerson.getColumn("disabled"));

		Table tableType = this.createTable("type");
		tableType.createColumn("type_key", new IntDataType());
		tableType.createColumn("type", new VarCharDataType(100));
		this.createPrimaryKeyConstraint(tableType, tableType.getColumn("type_key"));
		this.createNotNullConstraint(tableType, tableType.getColumn("type_key"));
		this.createNotNullConstraint(tableType, tableType.getColumn("type"));

		Table tableNameserver = this.createTable("nameserver");
		tableNameserver.createColumn("nameserver_key", new IntDataType());
		tableNameserver.createColumn("nameserver", new VarCharDataType(255));
		tableNameserver.createColumn("domain_fkey", new IntDataType());
		this.createPrimaryKeyConstraint(tableNameserver, tableNameserver.getColumn("nameserver_key"));
		this.createNotNullConstraint(tableNameserver, tableNameserver.getColumn("nameserver_key"));
		this.createNotNullConstraint(tableNameserver, tableNameserver.getColumn("nameserver"));
		this.createNotNullConstraint(tableNameserver, tableNameserver.getColumn("domain_fkey"));

		Table tableCountry = this.createTable("country");
		tableCountry.createColumn("country_key", new IntDataType());
		tableCountry.createColumn("short", new VarCharDataType(2));
		tableCountry.createColumn("country", new VarCharDataType(255));
		this.createPrimaryKeyConstraint(tableCountry, tableCountry.getColumn("country_key"));
		this.createNotNullConstraint(tableCountry, tableCountry.getColumn("country_key"));
		this.createNotNullConstraint(tableCountry, tableCountry.getColumn("short"));
		this.createNotNullConstraint(tableCountry, tableCountry.getColumn("country"));
	}
}

