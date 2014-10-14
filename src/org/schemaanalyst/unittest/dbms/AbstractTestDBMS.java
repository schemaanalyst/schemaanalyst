package org.schemaanalyst.unittest.dbms;

import org.schemaanalyst.dbms.DatabaseInteractor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by phil on 11/08/2014.
 */
public abstract class AbstractTestDBMS {

    public static final boolean DEBUG = false;

    public abstract DatabaseInteractor getDatabaseInteractor();

    public void singleColumnPrimaryKeyConstraintTest(boolean[] expectedResults) {
        singleColumnPrimaryKeyConstraintTest("INT", "1", expectedResults);
    }

    public void singleColumnPrimaryKeyConstraintTest(String colType, String value, boolean[] expectedResults) {
        String[] setupStatements = {
                "DROP TABLE IF EXISTS t",
                "CREATE TABLE t(c1 " + colType + " PRIMARY KEY)"
        };

        String[] testStatements = {
                "INSERT INTO t(c1) VALUES(NULL)",
                "INSERT INTO t(c1) VALUES(NULL)",
                "INSERT INTO t(c1) VALUES(" + value + ")",
                "INSERT INTO t(c1) VALUES(" + value + ")"
        };

        executeTest("Single column PRIMARY KEY test (with " + colType + ")", setupStatements, testStatements, expectedResults);
    }

    public void multiColumnPrimaryKeyConstraintTest(boolean[] expectedResults) {
        String[] setupStatements = {
                "DROP TABLE IF EXISTS t",
                "CREATE TABLE t(c1 INT, c2 INT, PRIMARY KEY(c1, c2))"
        };

        String[] testStatements = {
                "INSERT INTO t(c1, c2) VALUES(NULL, NULL)",
                "INSERT INTO t(c1, c2) VALUES(NULL, NULL)",
                "INSERT INTO t(c1, c2) VALUES(NULL, 1)",
                "INSERT INTO t(c1, c2) VALUES(1, NULL)",
                "INSERT INTO t(c1, c2) VALUES(1, 1)",
                "INSERT INTO t(c1, c2) VALUES(1, 1)"
        };

        executeTest("Multi-column PRIMARY KEY test", setupStatements, testStatements, expectedResults);
    }


    public void multiColumnUniqueConstraintTest(boolean[] expectedResults) {
        String[] setupStatements = {
                "DROP TABLE IF EXISTS t",
                "CREATE TABLE t(c1 INT, c2 INT, UNIQUE(c1, c2))"
        };

        String[] testStatements = {
                "INSERT INTO t(c1, c2) VALUES(NULL, NULL)",
                "INSERT INTO t(c1, c2) VALUES(NULL, NULL)",
                "INSERT INTO t(c1, c2) VALUES(NULL, 1)",
                "INSERT INTO t(c1, c2) VALUES(1, NULL)",
                "INSERT INTO t(c1, c2) VALUES(1, 1)",
                "INSERT INTO t(c1, c2) VALUES(1, 1)"
        };

        executeTest("Multi-column UNIQUE test", setupStatements, testStatements, expectedResults);
    }

    public void multiColumnCheckConstraintTest(boolean[] expectedResults) {
        String[] setupStatements = {
                "DROP TABLE IF EXISTS t",
                "CREATE TABLE t(c1 INT, c2 INT, CHECK(c1 = 0 OR c1 > c2))"
        };

        String[] testStatements = {
                "INSERT INTO t(c1, c2) VALUES(NULL, NULL)",
                "INSERT INTO t(c1, c2) VALUES(NULL, 1)",
                "INSERT INTO t(c1, c2) VALUES(0, NULL)",
                "INSERT INTO t(c1, c2) VALUES(1, NULL)",
                "INSERT INTO t(c1, c2) VALUES(0, -1)",
                "INSERT INTO t(c1, c2) VALUES(1, 0)",
                "INSERT INTO t(c1, c2) VALUES(1, 2)"
        };

        executeTest("Multi-column CHECK test", setupStatements, testStatements, expectedResults);
    }

    public void multiColumnForeignKeyConstraintTest(boolean[] expectedResults) {
        String[] setupStatements = {
                "DROP TABLE IF EXISTS t2",
                "DROP TABLE IF EXISTS t1",
                "CREATE TABLE t1(c1 INT, c2 INT, PRIMARY KEY(c1, c2))",
                "CREATE TABLE t2(c1 INT, c2 INT, FOREIGN KEY(c1, c2) REFERENCES t1(c1, c2))"
        };

        String[] testStatements = {
                "INSERT INTO t1(c1, c2) VALUES(1, 1)",
                "INSERT INTO t2(c1, c2) VALUES(1, 1)",
                "INSERT INTO t2(c1, c2) VALUES(NULL, NULL)",
                "INSERT INTO t2(c1, c2) VALUES(NULL, NULL)",
                "INSERT INTO t2(c1, c2) VALUES(2, NULL)",
                "INSERT INTO t2(c1, c2) VALUES(NULL, 2)",
                "INSERT INTO t2(c1, c2) VALUES(2, 2)"
        };

        executeTest("Multi-column FOREIGN KEY test", setupStatements, testStatements, expectedResults);
    }

    public void executeTest(String message, String[] setupStatements, String[] testStatements, boolean[] expectedResults) {
        DatabaseInteractor databaseInteractor = getDatabaseInteractor();

        if (expectedResults.length != testStatements.length) {
            throw new RuntimeException(message + ": expectedResults array not same size as testStatements");
        }

        for (int i=0; i < setupStatements.length; i++) {
            Integer returnValue = databaseInteractor.execute(setupStatements[i]);
            String messageForAssert = message + ", setup statement " + i + " failed (" + setupStatements[i] + ") (return value was " + returnValue + ")";

            if (DEBUG) {
                System.out.println(setupStatements[i]);
                System.out.println("-- " + returnValue);
            }

            assertTrue(messageForAssert, returnValue >= 0);
        }

        for (int i=0; i < testStatements.length; i++) {
            Integer returnValue = databaseInteractor.execute(testStatements[i]);
            String messageForTest = message + ", test statement " + i + " failed (" + testStatements[i] + ") (return value was " + returnValue + ", expected result was " + expectedResults[i] + ")";

            if (DEBUG) {
                System.out.println(testStatements[i]);
                System.out.println("-- " + returnValue);
            }

            if (returnValue == 1) {
                assertTrue(messageForTest, expectedResults[i]);
            } else {
                assertFalse(messageForTest, expectedResults[i]);
            }
        }
    }
}
