package paper.ast;
import org.schemaanalyst.sqlrepresentation.Schema;
import paper.util.SchemaStatsTable;
import parsedcasestudy.*;

public class LatexSchemaStatsTable extends SchemaStatsTable {

    public static Schema[] schemas = {
            new BankAccount(),
            new BrowserCookies(),
            new CoffeeOrders(),
            new CustomerOrder(),
            new Employee(),
            new Examination(),
            new Flights(),
            new Inventory(),
            new Iso3166(),
            new JWhoisServer(),
            new MozillaPermissions(),
            new NistWeather(),
            new NistXTS749(),
            new Person(),
            new Products(),
            new StudentResidence()
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
