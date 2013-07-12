package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * Iso3166 schema.
 * Java code originally generated: 2013/07/11 14:08:42
 *
 */
@SuppressWarnings("serial")
public class Iso3166 extends Schema {

    public Iso3166() {
        super("Iso3166");

        Table tableCountry = this.createTable("country");
        tableCountry.addColumn("name", new VarCharDataType(100));
        tableCountry.addColumn("two_letter", new VarCharDataType(100));
        tableCountry.addColumn("country_id", new IntDataType());
        tableCountry.setPrimaryKeyConstraint(tableCountry.getColumn("two_letter"));
        tableCountry.addNotNullConstraint(tableCountry.getColumn("name"));
        tableCountry.addNotNullConstraint(tableCountry.getColumn("country_id"));
    }
}
