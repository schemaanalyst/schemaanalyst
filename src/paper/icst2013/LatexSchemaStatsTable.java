package paper.icst2013;

import org.schemaanalyst.sqlrepresentation.Schema;
import paper.util.SchemaStatsTable;

public class LatexSchemaStatsTable extends SchemaStatsTable {

    public static Schema[] schemas = {
        // insert schemas
    };

    public LatexSchemaStatsTable() {
        super(" & ", " \\\\\n");
    }

    @Override
    protected void writeHeader(StringBuffer table) {
        table.append("%!TEX root=../../icst13-schemaanalyst.tex\n");
    }

    @Override
    protected void writeFooter(StringBuffer table,
            int totalNumTables, int totalNumColumns, // int totalUniqueColumnTypes,
            int totalNumChecks, int totalNumForeignKeys, int totalNumNotNulls,
            int totalNumPrimaryKeys, int totalNumUniques) {
        table.append("\\midrule \n");

        writeRow(table, "{\\bf Total}", totalNumTables, totalNumColumns, // totalUniqueColumnTypes, 
                totalNumChecks, totalNumForeignKeys, totalNumNotNulls, totalNumPrimaryKeys, totalNumUniques);
    }

    public static void main(String[] args) {
        LatexSchemaStatsTable table = new LatexSchemaStatsTable();
        System.out.println(table.write(schemas));
    }
}
