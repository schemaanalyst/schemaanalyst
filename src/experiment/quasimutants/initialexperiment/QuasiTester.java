/*
 */
package experiment.quasimutants.initialexperiment;

import java.sql.SQLSyntaxErrorException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.dbms.hsqldb.HSQLDB;
import org.schemaanalyst.deprecated.Configuration;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;

import originalcasestudy.*;

/**
 *
 * @author chris
 */
public class QuasiTester {

    /**
     * Schemas with HSQLDB QMs: BankAccount BookTown CoffeeOrders CustomerOrder
     * Employee Examination Flights FrenchTowns Inventory Iso3166 JWhoisServer
     * NistDML181 NistDML182 NistWeather NistXTS748 NistXTS749 Person Products
     * RiskIt StudentResidence UnixUsage
     */
    static Schema[] schemas = {
        new BankAccount(),
        new BookTown(),
        new CoffeeOrders(),
        new CustomerOrder(),
        new Employee(),
        new Examination(),
        new Flights(),
        new FrenchTowns(),
        new Inventory(),
        new Iso3166(),
        new JWhoisServer(),
        new NistDML181(),
        new NistDML182(),
        new NistWeather(),
        new NistXTS748(),
        new NistXTS749(),
        new Person(),
        new Products(),
        new RiskIt(),
        new StudentResidence(),
        new UnixUsage()
    };

    public static void main(String[] args) {
        // Set config parameters
        Configuration.debug = false;
        Configuration.project = System.getProperty("user.dir") + "/";
        Configuration.type = "quasi-test";
        Configuration.database = "quasi-test";
        Logger.getLogger("hsqldb.db").setLevel(Level.WARNING);

        // Test the mutants
        DBMS db = new HSQLDB();
        DatabaseInteractor dbInteractor = db.getDatabaseInteractor();
        dbInteractor.initializeDatabaseConnection();
        SQLWriter dbWriter = db.getSQLWriter();
        for (Schema s : schemas) {
            List<Schema> mutants = new ICSTMutator().produceMutants(s);
            for (Schema m : mutants) {
                dbInteractor.execute(collapse(dbWriter.writeCreateTableStatements(m)));
                dbInteractor.execute(collapse(dbWriter.writeDropTableStatements(m, true)));
            }
        }
    }

    private static String collapse(List<String> strings) {
        StringBuilder b = new StringBuilder();
        for (String s : strings) {
            b.append(s);
            b.append("\n");
        }
        return b.toString();
    }
}
