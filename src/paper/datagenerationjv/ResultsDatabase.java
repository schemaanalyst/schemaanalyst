package paper.datagenerationjv;

import org.sqlite.SQLiteConfig;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        sql += " ORDER BY name COLLATE NOCASE ASC";

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

    public boolean alreadyDoneExpt(String schemaName, String coverageCriterionName, String dataGeneratorName,
                                   String dbmsName, int runNo) {

        String sql = "SELECT COUNT(*) AS count FROM test_generation_runs WHERE "
                   + "schema_name='" + schemaName + "' AND coverage_criterion_name = '" + coverageCriterionName + "' AND "
                   + "data_generator_name = '" + dataGeneratorName + "' AND dbms_name = '" + dbmsName + "' AND run_no = '" + runNo + "'";

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("count") == 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Could not get count from db");
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

    public List<String> getTestSuiteFileNames(String searchName) {
        String sql = "select schema_name, coverage_criterion_name, dbms_name, run_no from test_generation_runs where data_generator_name='" + searchName + "'";
        List<String> files = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String schemaName = rs.getString("schema_name");
                String coverageCriterionName = rs.getString("coverage_criterion_name");

                if (!ProcessTestSuiteFiles.exptNotNeeded(schemaName, coverageCriterionName)) {
                    String file = schemaName + "-" + coverageCriterionName + "-" + searchName + "-" + rs.getString("dbms_name") + "-" + rs.getString("run_no");
                    files.add(file);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return files;
    }
}
