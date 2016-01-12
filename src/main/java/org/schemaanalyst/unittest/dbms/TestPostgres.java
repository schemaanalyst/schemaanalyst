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
public class TestPostgres extends AbstractTestDBMS {

    protected DatabaseInteractor databaseInteractor;

    @Override
    public DatabaseInteractor getDatabaseInteractor() {
        if (databaseInteractor == null) {
            DBMS postgresDBMS = DBMSFactory.instantiate("Postgres");
            databaseInteractor = postgresDBMS.getDatabaseInteractor(
                    "test",
                    new DatabaseConfiguration(),
                    new LocationsConfiguration());
        }
        return databaseInteractor;
    }

    @Test
    public void singleColumnPrimaryKeyConstraintTest() {
        boolean[] results = {
                false, // 0. INSERT INTO t(c1) VALUES(NULL)
                false, // 1. INSERT INTO t(c1) VALUES(NULL)
                true,  // 2. INSERT INTO t(c1) VALUES(VAL)
                false  // 3. INSERT INTO t(c1) VALUES(VAL)
        };
        singleColumnPrimaryKeyConstraintTest(results);
    }

    @Test
    public void multiColumnPrimaryKeyConstraintTest() {
        boolean[] results = {
                false, // 0. INSERT INTO t(c1, c2) VALUES(NULL, NULL)
                false, // 1. INSERT INTO t(c1, c2) VALUES(NULL, NULL)
                false, // 2. INSERT INTO t(c1, c2) VALUES(NULL, 1)
                false, // 3. INSERT INTO t(c1, c2) VALUES(1, NULL)
                true,  // 4. INSERT INTO t(c1, c2) VALUES(1, 1)
                false  // 5. INSERT INTO t(c1, c2) VALUES(1, 1)
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
