package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.SmallIntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.TimestampDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * JWhoisServer schema.
 * Java code originally generated: 2013/07/11 14:08:59
 *
 */
@SuppressWarnings("serial")
public class JWhoisServer extends Schema {

    public JWhoisServer() {
        super("JWhoisServer");

        Table tableDomain = this.createTable("domain");
        tableDomain.addColumn("domain_key", new IntDataType());
        tableDomain.addColumn("domain", new VarCharDataType(255));
        tableDomain.addColumn("registered_date", new TimestampDataType());
        tableDomain.addColumn("registerexpire_date", new TimestampDataType());
        tableDomain.addColumn("changed", new TimestampDataType());
        tableDomain.addColumn("remarks", new VarCharDataType(255));
        tableDomain.addColumn("holder", new IntDataType());
        tableDomain.addColumn("admin_c", new IntDataType());
        tableDomain.addColumn("tech_c", new IntDataType());
        tableDomain.addColumn("zone_c", new IntDataType());
        tableDomain.addColumn("mntnr_fkey", new IntDataType());
        tableDomain.addColumn("publicviewabledata", new SmallIntDataType());
        tableDomain.addColumn("disabled", new SmallIntDataType());
        tableDomain.setPrimaryKeyConstraint(tableDomain.getColumn("domain_key"));
        tableDomain.addNotNullConstraint(tableDomain.getColumn("domain_key"));
        tableDomain.addNotNullConstraint(tableDomain.getColumn("domain"));
        tableDomain.addNotNullConstraint(tableDomain.getColumn("registered_date"));
        tableDomain.addNotNullConstraint(tableDomain.getColumn("registerexpire_date"));
        tableDomain.addNotNullConstraint(tableDomain.getColumn("changed"));
        tableDomain.addNotNullConstraint(tableDomain.getColumn("holder"));
        tableDomain.addNotNullConstraint(tableDomain.getColumn("admin_c"));
        tableDomain.addNotNullConstraint(tableDomain.getColumn("tech_c"));
        tableDomain.addNotNullConstraint(tableDomain.getColumn("zone_c"));
        tableDomain.addNotNullConstraint(tableDomain.getColumn("mntnr_fkey"));
        tableDomain.addNotNullConstraint(tableDomain.getColumn("publicviewabledata"));
        tableDomain.addNotNullConstraint(tableDomain.getColumn("disabled"));

        Table tableMntnr = this.createTable("mntnr");
        tableMntnr.addColumn("mntnr_key", new IntDataType());
        tableMntnr.addColumn("login", new VarCharDataType(255));
        tableMntnr.addColumn("password", new VarCharDataType(255));
        tableMntnr.addColumn("name", new VarCharDataType(255));
        tableMntnr.addColumn("address", new VarCharDataType(255));
        tableMntnr.addColumn("pcode", new VarCharDataType(20));
        tableMntnr.addColumn("city", new VarCharDataType(255));
        tableMntnr.addColumn("country_fkey", new IntDataType());
        tableMntnr.addColumn("phone", new VarCharDataType(100));
        tableMntnr.addColumn("fax", new VarCharDataType(100));
        tableMntnr.addColumn("email", new VarCharDataType(255));
        tableMntnr.addColumn("remarks", new VarCharDataType(255));
        tableMntnr.addColumn("changed", new TimestampDataType());
        tableMntnr.addColumn("disabled", new SmallIntDataType());
        tableMntnr.setPrimaryKeyConstraint(tableMntnr.getColumn("mntnr_key"));
        tableMntnr.addNotNullConstraint(tableMntnr.getColumn("mntnr_key"));
        tableMntnr.addNotNullConstraint(tableMntnr.getColumn("login"));
        tableMntnr.addNotNullConstraint(tableMntnr.getColumn("password"));
        tableMntnr.addNotNullConstraint(tableMntnr.getColumn("name"));
        tableMntnr.addNotNullConstraint(tableMntnr.getColumn("address"));
        tableMntnr.addNotNullConstraint(tableMntnr.getColumn("pcode"));
        tableMntnr.addNotNullConstraint(tableMntnr.getColumn("city"));
        tableMntnr.addNotNullConstraint(tableMntnr.getColumn("country_fkey"));
        tableMntnr.addNotNullConstraint(tableMntnr.getColumn("phone"));
        tableMntnr.addNotNullConstraint(tableMntnr.getColumn("email"));
        tableMntnr.addNotNullConstraint(tableMntnr.getColumn("changed"));
        tableMntnr.addNotNullConstraint(tableMntnr.getColumn("disabled"));

        Table tablePerson = this.createTable("person");
        tablePerson.addColumn("person_key", new IntDataType());
        tablePerson.addColumn("type_fkey", new IntDataType());
        tablePerson.addColumn("name", new VarCharDataType(255));
        tablePerson.addColumn("address", new VarCharDataType(255));
        tablePerson.addColumn("pcode", new VarCharDataType(20));
        tablePerson.addColumn("city", new VarCharDataType(255));
        tablePerson.addColumn("country_fkey", new IntDataType());
        tablePerson.addColumn("phone", new VarCharDataType(100));
        tablePerson.addColumn("fax", new VarCharDataType(100));
        tablePerson.addColumn("email", new VarCharDataType(255));
        tablePerson.addColumn("remarks", new VarCharDataType(255));
        tablePerson.addColumn("changed", new TimestampDataType());
        tablePerson.addColumn("mntnr_fkey", new IntDataType());
        tablePerson.addColumn("disabled", new SmallIntDataType());
        tablePerson.setPrimaryKeyConstraint(tablePerson.getColumn("person_key"));
        tablePerson.addNotNullConstraint(tablePerson.getColumn("person_key"));
        tablePerson.addNotNullConstraint(tablePerson.getColumn("type_fkey"));
        tablePerson.addNotNullConstraint(tablePerson.getColumn("name"));
        tablePerson.addNotNullConstraint(tablePerson.getColumn("address"));
        tablePerson.addNotNullConstraint(tablePerson.getColumn("pcode"));
        tablePerson.addNotNullConstraint(tablePerson.getColumn("city"));
        tablePerson.addNotNullConstraint(tablePerson.getColumn("country_fkey"));
        tablePerson.addNotNullConstraint(tablePerson.getColumn("phone"));
        tablePerson.addNotNullConstraint(tablePerson.getColumn("email"));
        tablePerson.addNotNullConstraint(tablePerson.getColumn("changed"));
        tablePerson.addNotNullConstraint(tablePerson.getColumn("mntnr_fkey"));
        tablePerson.addNotNullConstraint(tablePerson.getColumn("disabled"));

        Table tableType = this.createTable("type");
        tableType.addColumn("type_key", new IntDataType());
        tableType.addColumn("type", new VarCharDataType(100));
        tableType.setPrimaryKeyConstraint(tableType.getColumn("type_key"));
        tableType.addNotNullConstraint(tableType.getColumn("type_key"));
        tableType.addNotNullConstraint(tableType.getColumn("type"));

        Table tableNameserver = this.createTable("nameserver");
        tableNameserver.addColumn("nameserver_key", new IntDataType());
        tableNameserver.addColumn("nameserver", new VarCharDataType(255));
        tableNameserver.addColumn("domain_fkey", new IntDataType());
        tableNameserver.setPrimaryKeyConstraint(tableNameserver.getColumn("nameserver_key"));
        tableNameserver.addNotNullConstraint(tableNameserver.getColumn("nameserver_key"));
        tableNameserver.addNotNullConstraint(tableNameserver.getColumn("nameserver"));
        tableNameserver.addNotNullConstraint(tableNameserver.getColumn("domain_fkey"));

        Table tableCountry = this.createTable("country");
        tableCountry.addColumn("country_key", new IntDataType());
        tableCountry.addColumn("short", new VarCharDataType(2));
        tableCountry.addColumn("country", new VarCharDataType(255));
        tableCountry.setPrimaryKeyConstraint(tableCountry.getColumn("country_key"));
        tableCountry.addNotNullConstraint(tableCountry.getColumn("country_key"));
        tableCountry.addNotNullConstraint(tableCountry.getColumn("short"));
        tableCountry.addNotNullConstraint(tableCountry.getColumn("country"));
    }
}
