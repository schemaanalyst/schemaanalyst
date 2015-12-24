package paper.ineffectivemutants.manualevaluation.classified;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import paper.ineffectivemutants.manualevaluation.ManualAnalysisTestSuite;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CustomerOrder_HyperSQL_56_NORMAL extends ManualAnalysisTestSuite {

    @BeforeClass
    public static void initialise() throws ClassNotFoundException, SQLException {
        // load the JDBC driver and create the connection and statement object used by this test suite
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        connection = DriverManager.getConnection("jdbc:hsqldb:mem:/database;hsqldb.write_delay=false");

        // tell HyperSQL to always persist the data right away
        connection.setAutoCommit(true);
        // create the statement
        statement = connection.createStatement();
    }

    @AfterClass
    public static void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    protected String getSchemaName() {
        return "CustomerOrder";
    }

    protected String getDBMSName() {
        return "HyperSQL";
    }

    protected int getMutantNumberBeingEvaluated() {
        return 56;
    }

    protected int getLastMutantNumber() {
        return 91;
    }

    @After
    public void dropTables() throws SQLException {
        statement.executeUpdate("DROP TABLE IF EXISTS \"db_order_item\"");
        statement.executeUpdate("DROP TABLE IF EXISTS \"db_order\"");
        statement.executeUpdate("DROP TABLE IF EXISTS \"db_customer\"");
        statement.executeUpdate("DROP TABLE IF EXISTS \"db_user\"");
        statement.executeUpdate("DROP TABLE IF EXISTS \"db_role\"");
        statement.executeUpdate("DROP TABLE IF EXISTS \"db_product\"");
        statement.executeUpdate("DROP TABLE IF EXISTS \"db_category\"");
    }

    /*****************************/
    /*** BEGIN MANUAL ANALYSIS ***/
    /*****************************/

    String statement1 = "INSERT INTO \"db_role\"(" +
            "    \"name\"" +
            ") VALUES (" +
            "    ''" +
            ")";
    String statement2 = "INSERT INTO \"db_user\"(" +
            "    \"id\", \"name\", \"email\", \"password\", \"role_id\", \"active\"" +
            ") VALUES (" +
            "    0, '', '', '', '', 0" +
            ")";
    String statement3 = "INSERT INTO \"db_customer\"(" +
            "    \"id\", \"category\", \"salutation\", \"first_name\", \"last_name\", \"birth_date\"" +
            ") VALUES (" +
            "    0, '', '', '', '', '2000-01-01'" +
            ")";

    String statement4 = "INSERT INTO \"db_customer\"(" +
            "    \"id\", \"category\", \"salutation\", \"first_name\", \"last_name\", \"birth_date\"" +
            ") VALUES (" +
            "    0, '', '', NULL, '', '2000-01-01'" +
            ")";

    @Test
    public void notImpaired() throws SQLException {
        assertTrue(insertToMutant(statement1, statement2, statement3));
    }

    @Test
    public void notEquivalent() throws SQLException {
        assertTrue(originalAndMutantHaveDifferentBehavior(statement1, statement2, statement4));
    }

    @Test
    public void notRedundant() throws SQLException {
        assertEquals(mutantAndOtherMutantsHaveDifferentBehavior(statement1, statement2, statement4), SUCCESS);
    }

    // ENTER END VERDICT (delete as appropriate): normal

    /*****************************/
    /***  END MANUAL ANALYSIS  ***/
    /*****************************/

}

