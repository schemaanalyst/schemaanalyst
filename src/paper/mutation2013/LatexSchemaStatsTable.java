package paper.mutation2013;

import org.schemaanalyst.sqlrepresentation.Schema;
import paper.util.SchemaStatsTable;

public class LatexSchemaStatsTable extends SchemaStatsTable {

    public static Schema[] schemas = {
        // schemas removed as original case studies deleted!        
    };

    public LatexSchemaStatsTable() {
        super(" & ", " \\\\\n");
    }

    @Override
    protected void writeHeader(StringBuffer table) {
        table.append("%!TEX root=mut13-schemata.tex\n");
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
