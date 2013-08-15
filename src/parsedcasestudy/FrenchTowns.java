package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * FrenchTowns schema.
 * Java code originally generated: 2013/08/15 10:51:50
 *
 */

@SuppressWarnings("serial")
public class FrenchTowns extends Schema {

	public FrenchTowns() {
		super("FrenchTowns");

		Table tableRegions = this.createTable("Regions");
		tableRegions.createColumn("id", new IntDataType());
		tableRegions.createColumn("code", new VarCharDataType(4));
		tableRegions.createColumn("capital", new VarCharDataType(10));
		tableRegions.createColumn("name", new VarCharDataType(100));
		tableRegions.createNotNullConstraint(tableRegions.getColumn("id"));
		tableRegions.createNotNullConstraint(tableRegions.getColumn("code"));
		tableRegions.createNotNullConstraint(tableRegions.getColumn("capital"));
		tableRegions.createNotNullConstraint(tableRegions.getColumn("name"));
		tableRegions.createUniqueConstraint(tableRegions.getColumn("id"));
		tableRegions.createUniqueConstraint(tableRegions.getColumn("code"));
		tableRegions.createUniqueConstraint(tableRegions.getColumn("name"));

		Table tableDepartments = this.createTable("Departments");
		tableDepartments.createColumn("id", new IntDataType());
		tableDepartments.createColumn("code", new VarCharDataType(4));
		tableDepartments.createColumn("capital", new VarCharDataType(10));
		tableDepartments.createColumn("region", new VarCharDataType(4));
		tableDepartments.createColumn("name", new VarCharDataType(100));
		tableDepartments.createForeignKeyConstraint(tableDepartments.getColumn("region"), tableRegions, tableDepartments.getColumn("code"));
		tableDepartments.createNotNullConstraint(tableDepartments.getColumn("id"));
		tableDepartments.createNotNullConstraint(tableDepartments.getColumn("code"));
		tableDepartments.createNotNullConstraint(tableDepartments.getColumn("capital"));
		tableDepartments.createNotNullConstraint(tableDepartments.getColumn("region"));
		tableDepartments.createNotNullConstraint(tableDepartments.getColumn("name"));
		tableDepartments.createUniqueConstraint(tableDepartments.getColumn("id"));
		tableDepartments.createUniqueConstraint(tableDepartments.getColumn("code"));
		tableDepartments.createUniqueConstraint(tableDepartments.getColumn("capital"));
		tableDepartments.createUniqueConstraint(tableDepartments.getColumn("name"));

		Table tableTowns = this.createTable("Towns");
		tableTowns.createColumn("id", new IntDataType());
		tableTowns.createColumn("code", new VarCharDataType(10));
		tableTowns.createColumn("article", new VarCharDataType(100));
		tableTowns.createColumn("name", new VarCharDataType(100));
		tableTowns.createColumn("department", new VarCharDataType(4));
		tableTowns.createForeignKeyConstraint(tableTowns.getColumn("department"), tableDepartments, tableTowns.getColumn("code"));
		tableTowns.createNotNullConstraint(tableTowns.getColumn("id"));
		tableTowns.createNotNullConstraint(tableTowns.getColumn("code"));
		tableTowns.createNotNullConstraint(tableTowns.getColumn("name"));
		tableTowns.createNotNullConstraint(tableTowns.getColumn("department"));
		tableTowns.createUniqueConstraint(tableTowns.getColumn("id"));
		tableTowns.createUniqueConstraint(tableTowns.getColumn("code"), tableTowns.getColumn("department"));
	}
}

