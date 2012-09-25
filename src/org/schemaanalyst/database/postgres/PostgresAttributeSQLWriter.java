package org.schemaanalyst.database.postgres;

import org.schemaanalyst.schema.attribute.Attribute;
import org.schemaanalyst.schema.attribute.AttributeVisitor;
import org.schemaanalyst.schema.attribute.AutoIncrementAttribute;
import org.schemaanalyst.schema.attribute.DefaultAttribute;
import org.schemaanalyst.schema.attribute.IdentityAttribute;
import org.schemaanalyst.sqlwriter.AttributeSQLWriter;

public class PostgresAttributeSQLWriter extends AttributeSQLWriter {
    // Postgres cannot properly handle the IDENTITY attribute 
    // unless you define a SEQUENCE and reference it; for now, 
    // we are only going to cause Postgres to not output anything;
    // this does not negatively influence test data generation
    public String writeIdentityAttribute(IdentityAttribute identity) {
	return "";
    }
}
