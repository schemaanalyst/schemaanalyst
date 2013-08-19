package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * FrenchTowns schema.
 * Java code originally generated: 2013/08/17 00:30:38
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
		this.createNotNullConstraint(tableRegions, tableRegions.getColumn("id"));
		this.createNotNullConstraint(tableRegions, tableRegions.getColumn("code"));
		this.createNotNullConstraint(tableRegions, tableRegions.getColumn("capital"));
		this.createNotNullConstraint(tableRegions, tableRegions.getColumn("name"));
		this.createUniqueConstraint(tableRegions, tableRegions.getColumn("id"));
		this.createUniqueConstraint(tableRegions, tableRegions.getColumn("code"));
		this.createUniqueConstraint(tableRegions, tableRegions.getColumn("name"));

		Table tableDepartments = this.createTable("Departments");
		tableDepartments.createColumn("id", new IntDataType());
		tableDepartments.createColumn("code", new VarCharDataType(4));
		tableDepartments.createColumn("capital", new VarCharDataType(10));
		tableDepartments.createColumn("region", new VarCharDataType(4));
		tableDepartments.createColumn("name", new VarCharDataType(100));
		this.createForeignKeyConstraint(tableDepartments, tableDepartments.getColumn("region"), tableRegions, tableRegions.getColumn("code"));
		this.createNotNullConstraint(tableDepartments, tableDepartments.getColumn("id"));
		this.createNotNullConstraint(tableDepartments, tableDepartments.getColumn("code"));
		this.createNotNullConstraint(tableDepartments, tableDepartments.getColumn("capital"));
		this.createNotNullConstraint(tableDepartments, tableDepartments.getColumn("region"));
		this.createNotNullConstraint(tableDepartments, tableDepartments.getColumn("name"));
		this.createUniqueConstraint(tableDepartments, tableDepartments.getColumn("id"));
		this.createUniqueConstraint(tableDepartments, tableDepartments.getColumn("code"));
		this.createUniqueConstraint(tableDepartments, tableDepartments.getColumn("capital"));
		this.createUniqueConstraint(tableDepartments, tableDepartments.getColumn("name"));

		Table tableTowns = this.createTable("Towns");
		tableTowns.createColumn("id", new IntDataType());
		tableTowns.createColumn("code", new VarCharDataType(10));
		tableTowns.createColumn("article", new VarCharDataType(100));
		tableTowns.createColumn("name", new VarCharDataType(100));
		tableTowns.createColumn("department", new VarCharDataType(4));
		this.createForeignKeyConstraint(tableTowns, tableTowns.getColumn("department"), tableDepartments, tableDepartments.getColumn("code"));
		this.createNotNullConstraint(tableTowns, tableTowns.getColumn("id"));
		this.createNotNullConstraint(tableTowns, tableTowns.getColumn("code"));
		this.createNotNullConstraint(tableTowns, tableTowns.getColumn("name"));
		this.createNotNullConstraint(tableTowns, tableTowns.getColumn("department"));
		this.createUniqueConstraint(tableTowns, tableTowns.getColumn("id"));
		this.createUniqueConstraint(tableTowns, tableTowns.getColumn("code"), tableTowns.getColumn("department"));
	}
}

