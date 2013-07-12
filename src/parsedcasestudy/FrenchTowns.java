package parsedcasestudy;

import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.datatype.IntDataType;
import org.schemaanalyst.sqlrepresentation.datatype.VarCharDataType;

/*
 * FrenchTowns schema.
 * Java code originally generated: 2013/07/11 14:08:28
 *
 */
@SuppressWarnings("serial")
public class FrenchTowns extends Schema {

    public FrenchTowns() {
        super("FrenchTowns");

        Table tableRegions = this.createTable("Regions");
        tableRegions.addColumn("id", new IntDataType());
        tableRegions.addColumn("code", new VarCharDataType(4));
        tableRegions.addColumn("capital", new VarCharDataType(10));
        tableRegions.addColumn("name", new VarCharDataType(100));
        tableRegions.addNotNullConstraint(tableRegions.getColumn("id"));
        tableRegions.addNotNullConstraint(tableRegions.getColumn("code"));
        tableRegions.addNotNullConstraint(tableRegions.getColumn("capital"));
        tableRegions.addNotNullConstraint(tableRegions.getColumn("name"));
        tableRegions.addUniqueConstraint(tableRegions.getColumn("id"));
        tableRegions.addUniqueConstraint(tableRegions.getColumn("code"));
        tableRegions.addUniqueConstraint(tableRegions.getColumn("name"));

        Table tableDepartments = this.createTable("Departments");
        tableDepartments.addColumn("id", new IntDataType());
        tableDepartments.addColumn("code", new VarCharDataType(4));
        tableDepartments.addColumn("capital", new VarCharDataType(10));
        tableDepartments.addColumn("region", new VarCharDataType(4));
        tableDepartments.addColumn("name", new VarCharDataType(100));
        tableDepartments.addForeignKeyConstraint(tableDepartments.getColumn("region"), tableRegions, tableRegions.getColumn("code"));
        tableDepartments.addNotNullConstraint(tableDepartments.getColumn("id"));
        tableDepartments.addNotNullConstraint(tableDepartments.getColumn("code"));
        tableDepartments.addNotNullConstraint(tableDepartments.getColumn("capital"));
        tableDepartments.addNotNullConstraint(tableDepartments.getColumn("region"));
        tableDepartments.addNotNullConstraint(tableDepartments.getColumn("name"));
        tableDepartments.addUniqueConstraint(tableDepartments.getColumn("id"));
        tableDepartments.addUniqueConstraint(tableDepartments.getColumn("code"));
        tableDepartments.addUniqueConstraint(tableDepartments.getColumn("capital"));
        tableDepartments.addUniqueConstraint(tableDepartments.getColumn("name"));

        Table tableTowns = this.createTable("Towns");
        tableTowns.addColumn("id", new IntDataType());
        tableTowns.addColumn("code", new VarCharDataType(10));
        tableTowns.addColumn("article", new VarCharDataType(100));
        tableTowns.addColumn("name", new VarCharDataType(100));
        tableTowns.addColumn("department", new VarCharDataType(4));
        tableTowns.addForeignKeyConstraint(tableTowns.getColumn("department"), tableDepartments, tableDepartments.getColumn("code"));
        tableTowns.addNotNullConstraint(tableTowns.getColumn("id"));
        tableTowns.addNotNullConstraint(tableTowns.getColumn("code"));
        tableTowns.addNotNullConstraint(tableTowns.getColumn("name"));
        tableTowns.addNotNullConstraint(tableTowns.getColumn("department"));
        tableTowns.addUniqueConstraint(tableTowns.getColumn("id"));
        tableTowns.addUniqueConstraint(tableTowns.getColumn("code"), tableTowns.getColumn("department"));
    }
}
