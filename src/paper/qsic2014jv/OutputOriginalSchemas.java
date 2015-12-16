package paper.qsic2014jv;

import org.schemaanalyst.dbms.hypersql.HyperSQLSQLWriter;
import org.schemaanalyst.dbms.postgres.PostgresSQLWriter;
import org.schemaanalyst.dbms.sqlite.SQLiteSQLWriter;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;
import parsedcasestudy.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Writes the original schema file to the mutant directories
 * so that they can be compared with mutants.
 *
 * Created by phil on 16/12/2015.
 */
public class OutputOriginalSchemas {

    private static Schema[] schemas = {
            new ArtistSimilarity(),
            new ArtistTerm(),
            new BankAccount(),
            new BookTown(),
            new BrowserCookies(),
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
            new iTrust(),
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

    private static String BASE_DIR = "/Users/phil/Projects/schemaanalyst/ineffective-mutants/_data/_normalisation-reruns/mutants/";

    private static String MUTANT_DIR_SUFFIX = "-minus-stillborn/";

    private static String FILE = "0-original";

    private static String getSQL(SQLWriter sqlWriter, Schema schema) {
        List<String> createTableStatements = sqlWriter.writeCreateTableStatements(schema);
        String sql = "";
        for (String statement : createTableStatements) {
            sql += statement;
        }
        return sql;
    }

    private static void writeMutant(SQLWriter sqlWriter, Schema schema, String dbms) throws FileNotFoundException {
        String schemaName = schema.getClass().getSimpleName();

        String sql = "Original schema\n\n" + getSQL(sqlWriter, schema);

        String file = BASE_DIR + dbms + MUTANT_DIR_SUFFIX + schemaName + "/" + FILE;

        System.out.println("Wrote: " + file);
        PrintWriter out = new PrintWriter(file);
        out.println(sql);
        out.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        for (Schema schema : schemas) {
            SQLWriter sqlWriter = new PostgresSQLWriter();
            writeMutant(sqlWriter, schema, "postgres");

            sqlWriter = new HyperSQLSQLWriter();
            writeMutant(sqlWriter, schema, "hypersql");

            sqlWriter = new SQLiteSQLWriter();
            writeMutant(sqlWriter, schema, "sqlite");
        }
    }
}
