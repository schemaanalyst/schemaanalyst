package org.schemaanalyst.unittest.dbms;

import org.junit.Test;
import org.schemaanalyst.configuration.DatabaseConfiguration;
import org.schemaanalyst.configuration.LocationsConfiguration;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.dbms.DatabaseInteractor;

/**
 * Created by phil on 11/08/2014.
 */
public class TestSQLite extends AbstractTestDBMS {

    protected DatabaseInteractor databaseInteractor;

    @Override
    public DatabaseInteractor getDatabaseInteractor() {
        if (databaseInteractor == null) {
            DBMS sqlLiteDBMS = DBMSFactory.instantiate("SQLite");
            databaseInteractor = sqlLiteDBMS.getDatabaseInteractor(
                    "test",
                    new DatabaseConfiguration(),
                    new LocationsConfiguration());
        }
        return databaseInteractor;
    }

    @Test
    public void singleColumnPrimaryKeyConstraintTest() {
        boolean[] results = {
                true, // 0. INSERT INTO t(c1) VALUES(NULL)
                true, // 1. INSERT INTO t(c1) VALUES(NULL)
                true, // 2. INSERT INTO t(c1) VALUES(VAL)
                false // 3. INSERT INTO t(c1) VALUES(VAL)
        };
        singleColumnPrimaryKeyConstraintTest("INT", "1", results);
        singleColumnPrimaryKeyConstraintTest("TEXT", "'text'", results);

        boolean[] integerResults = {
                true,  // 0. INSERT INTO t(c1) VALUES(NULL)
                true,  // 1. INSERT INTO t(c1) VALUES(NULL)
                false, // 2. INSERT INTO t(c1) VALUES(VAL) -- fails since NULL from previous statement has already inserted 1 as a value.
                false  // 3. INSERT INTO t(c1) VALUES(VAL)
        };
        singleColumnPrimaryKeyConstraintTest("INTEGER", "1", integerResults);
    }

    @Test
    public void multiColumnPrimaryKeyConstraintTest() {
        boolean[] results = {
                true, // 0. INSERT INTO t(c1, c2) VALUES(NULL, NULL)
                true, // 1. INSERT INTO t(c1, c2) VALUES(NULL, NULL)
                true, // 2. INSERT INTO t(c1, c2) VALUES(NULL, 1)
                true, // 3. INSERT INTO t(c1, c2) VALUES(1, NULL)
                true, // 4. INSERT INTO t(c1, c2) VALUES(1, 1)
                false // 5. INSERT INTO t(c1, c2) VALUES(1, 1)
        };
        multiColumnPrimaryKeyConstraintTest(results);
    }

    @Test
    public void multiColumnUniqueConstraintTest() {
        boolean[] results = {
                true, // 0. INSERT INTO t(c1, c2) VALUES(NULL, NULL)
                true, // 1. INSERT INTO t(c1, c2) VALUES(NULL, NULL)
                true, // 2. INSERT INTO t(c1, c2) VALUES(NULL, 1)
                true, // 3. INSERT INTO t(c1, c2) VALUES(1, NULL)
                true, // 4. INSERT INTO t(c1, c2) VALUES(1, 1)
                false // 5. INSERT INTO t(c1, c2) VALUES(1, 1)
        };
        multiColumnUniqueConstraintTest(results);
    }

    @Test
    public void multiColumnCheckConstraintTest() {
        boolean[] results = {
                true, // 0. INSERT INTO t(c1, c2) VALUES(NULL, NULL)
                true, // 1. INSERT INTO t(c1, c2) VALUES(NULL, 1)
                true, // 2. INSERT INTO t(c1, c2) VALUES(0, NULL)
                true, // 3. INSERT INTO t(c1, c2) VALUES(1, NULL)
                true, // 4. INSERT INTO t(c1, c2) VALUES(0, -1)
                true, // 5. INSERT INTO t(c1, c2) VALUES(1, 0)
                false // 6. INSERT INTO t(c1, c2) VALUES(1, 2)
        };
        multiColumnCheckConstraintTest(results);
    }

    @Test
    public void multiColumnForeignKeyConstraintTest() {
        boolean[] results = {
                true, // 0. INSERT INTO t1(c1, c2) VALUES(1, 1)
                true, // 1. INSERT INTO t2(c1, c2) VALUES(1, 1)
                true, // 2. INSERT INTO t2(c1, c2) VALUES(NULL, NULL)
                true, // 3. INSERT INTO t2(c1, c2) VALUES(NULL, NULL)
                true, // 4. INSERT INTO t2(c1, c2) VALUES(2, NULL)
                true, // 5. INSERT INTO t2(c1, c2) VALUES(NULL, 2)
                false // 6. INSERT INTO t2(c1, c2) VALUES(2, 2)
        };
        multiColumnForeignKeyConstraintTest(results);
    }
}
