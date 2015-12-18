package paper.ineffectivemutants;

import paper.util.SchemaStatsTable;

public class LatexSchemaStatsTable extends SchemaStatsTable {

    public LatexSchemaStatsTable() {
        super(" & ", " \\\\\n");
    }

    @Override
    protected void writeHeader(StringBuffer table) {
    }

    @Override
    protected void writeFooter(StringBuffer table,
                               int totalNumTables, int totalNumColumns, // int totalUniqueColumnTypes,
                               int totalNumChecks, int totalNumForeignKeys, int totalNumNotNulls,
                               int totalNumPrimaryKeys, int totalNumUniques) {
        table.append("\\hline \n");

        writeRow(table, "Total", totalNumTables, totalNumColumns, // totalUniqueColumnTypes,
                totalNumChecks, totalNumForeignKeys, totalNumNotNulls, totalNumPrimaryKeys, totalNumUniques);
    }

    public static void main(String[] args) {
        LatexSchemaStatsTable table = new LatexSchemaStatsTable();
        System.out.println(table.write(Schemas.schemas));
    }
}
