package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * Iso3166 schema.
 * Java code originally generated: 2013/08/17 00:30:40
 *
 */

@SuppressWarnings("serial")
public class Iso3166 extends Schema {

	public Iso3166() {
		super("Iso3166");

		Table tableCountry = this.createTable("country");
		tableCountry.createColumn("name", new VarCharDataType(100));
		tableCountry.createColumn("two_letter", new VarCharDataType(100));
		tableCountry.createColumn("country_id", new IntDataType());
		this.createPrimaryKeyConstraint(tableCountry, tableCountry.getColumn("two_letter"));
		this.createNotNullConstraint(tableCountry, tableCountry.getColumn("name"));
		this.createNotNullConstraint(tableCountry, tableCountry.getColumn("country_id"));
	}
}

