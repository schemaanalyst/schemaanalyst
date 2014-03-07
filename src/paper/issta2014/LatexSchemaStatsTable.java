package paper.issta2014;

import org.schemaanalyst.sqlrepresentation.Schema;
import paper.util.SchemaStatsTable;
import parsedcasestudy.*;

public class LatexSchemaStatsTable extends SchemaStatsTable {

    public static Schema[] schemas = {
        new ArtistSimilarity(),
        new ArtistTerm(),
        new BankAccount(),
        new BookTown(),
        new Cloc(),
        new CoffeeOrders(),
        new FACAData1997Repaired(),
        new Flav_R03_1Repaired(),
        new Flights(),
        new GeoMetaDB(),
        new H1EFileFY2007Repaired(),
        new IsoFlav_R2Repaired(),
        new JWhoisServer(),
        new Mxm(),
        new NistDML181(),
        new NistDML182(),
        new NistDML183(),
        new NistWeather(),
        new NistXTS748(),
        new NistXTS749(),
        new RiskIt(),
        new SongTrackMetadata(),
        new StackOverflow(),
        new UnixUsage(),
        new WordNet()
    };

    public LatexSchemaStatsTable() {
        super(" & ", " \\\\\n");
    }

//    @Override
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
