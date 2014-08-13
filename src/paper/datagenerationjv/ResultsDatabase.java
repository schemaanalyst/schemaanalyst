package paper.datagenerationjv;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterion;
import org.schemaanalyst.testgeneration.coveragecriterion.CoverageCriterionFactory;
import org.sqlite.SQLiteConfig;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by phil on 13/08/2014.
 */
public class ResultsDatabase {

    private Connection connection;

    public ResultsDatabase(String resultsDatabaseLocation) {

        if (!new File(resultsDatabaseLocation).exists()) {
            throw new RuntimeException("Results database file \"" + resultsDatabaseLocation + "\" does not exist");
        }

        try {
            Class.forName("org.sqlite.JDBC");

            // enforce the foreign keys that are specified in the
            // relational schema; this could be turned off if you are
            // debugging and the data generation subsystem is not yet
            // generating data to satisfy the constraints.
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);

            // create the connection to the database,
            // using the properties for the SQLiteConfig
            // object to handle referential integrity's on/off switch
            connection = DriverManager.getConnection("jdbc:sqlite:" + resultsDatabaseLocation, config.toProperties());

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Schema> getSchemas() {
        Map<String, Schema> schemas = new HashMap<>();

        List<String> schemaClassNames = getNames("schemas");
        for (String schemaClassName : schemaClassNames) {
            try {
                Schema schema = (Schema) Class.forName("parsedcasestudy."+schemaClassName).newInstance();
                schemas.put(schemaClassName, schema);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return schemas;
    }

    public Map<String, CoverageCriterion> getCoverageCriteria(Schema schema) {
        Map<String, CoverageCriterion> coverageCriteria = new HashMap<>();

        List<String> coverageCriteriaClassNames = getNames("coverage_criteria");
        for (String coverageCriteriaClassName : coverageCriteriaClassNames) {
            CoverageCriterion coverageCriterion = CoverageCriterionFactory.integrityConstraintCriterion(coverageCriteriaClassName, schema);
            coverageCriteria.put(coverageCriteriaClassName, coverageCriterion);
        }

        return coverageCriteria;
    }

    public Map<String, DBMS> getDBMSs() {
        Map<String, DBMS> dbmses = new HashMap<>();

        List<String> dbmsNames = getNames("dbmses");
        for (String dbmsName : dbmsNames) {
            DBMS dbms = DBMSFactory.instantiate(dbmsName);
            dbmses.put(dbmsName, dbms);
        }

        return dbmses;
    }

    public List<String> getNames(String from) {
        return getNames(from, "");
    }

    public List<String> getNames(String from, String where) {
        String sql = "SELECT DISTINCT name FROM " + from;
        if (where != null && !where.equals("")) {
            sql += " WHERE " + where;
        }

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            List<String> classNames = new ArrayList<>();
            while (rs.next() ) {
                classNames.add(rs.getString("name"));
            }
            return classNames;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeInsert(String sql) {
        try {
            Statement statement = connection.createStatement();
            Integer result = statement.executeUpdate(sql);
            if (result != 1) {
                throw new RuntimeException("INSERT statement failed: " + sql);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
