package paper.qsic2014;

import org.schemaanalyst.sqlrepresentation.Schema;
import paper.util.SchemaStatsTable;
import parsedcasestudy.*;

public class LatexSchemaStatsTable extends SchemaStatsTable {

    public static Schema[] schemas = {
        new ArtistSimilarity(),
        new BookTown(),
        new Cloc(),
        new Flights(),
        new IsoFlav_R2Repaired(),
        new JWhoisServer(),
        new NistDML183(),
        new RiskIt(),
        new UnixUsage(),
        new WordNet()
    };

    public LatexSchemaStatsTable() {
        super(" & ", " \\\\\n");
    }

//  @Override
//    protected void writeHeader(StringBuffer table) {
//        table.append("%!TEX root=mut13-schemata.tex\n");
//    }
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
