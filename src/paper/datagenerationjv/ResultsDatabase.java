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

    public Long getSeed(int runNo) {
        String sql = "SELECT seed FROM seeds WHERE run_no = " + runNo;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                return rs.getLong("seed");
            } else {
                return null;
            }
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
