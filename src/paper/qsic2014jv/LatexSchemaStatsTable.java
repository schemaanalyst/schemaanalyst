package paper.qsic2014jv;

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
            new CustomerOrder(),
            new DellStore(),
            new Employee(),
            new Examination(),
            new Flights(),
            new FrenchTowns(),
            new Inventory(),
            new Iso3166(),
            new IsoFlav_R2Repaired(),
            new JWhoisServer(),
            new MozillaExtensions(),
            new MozillaPermissions(),
            new NistDML181(),
            new NistDML182(),
            new NistDML183(),
            new NistWeather(),
            new NistXTS748(),
            new NistXTS749(),
            new Person(),
            new Products(),
            new RiskIt(),
            new StackOverflow(),
            new StudentResidence(),
            new UnixUsage(),
            new Usda(),
            new WordNet()
    };

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
        System.out.println(table.write(schemas));
    }
}
