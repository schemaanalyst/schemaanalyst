/*
 */
package deprecated.mutation.analysis;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.util.runner.Runner;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.util.csv.CSVResult;
import org.schemaanalyst.util.csv.CSVWriter;
import org.schemaanalyst.util.runner.Description;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.xml.XMLSerialiser;

import org.schemaanalyst.mutation.analysis.result.SQLExecutionReport;
import org.schemaanalyst.mutation.analysis.result.SQLInsertRecord;
import deprecated.mutation.mutators.ConstraintMutatorWithoutFK;

/**
 * Run the 'Minimal Schemata' style of mutation analysis. This requires that the
 * result generation tool has been run, as it bases the mutation analysis on the
 * results produced by it.
 *
 * @author Chris J. Wright
 */
@Description("Runs the 'Minimal Schemata' style of mutation analysis. This "
        + "requires that the result generation tool has been run, as it bases "
        + "the mutation analysis on the results produced by it.")
@RequiredParameters("casestudy trial")
public class MinimalSchemata extends Runner {

    /**
     * The name of the schema to use.
     */
    @Parameter("The name of the schema to use.")
    protected String casestudy;
    /**
     * The number of the trial.
     */
    @Parameter("The number of the trial.")
    protected int trial;
    /**
     * The folder to retrieve the generated results.
     */
    @Parameter("The folder to retrieve the generated results.")
    protected String inputfolder; // Default in validate
    /**
     * The folder to write the results.
     */
    @Parameter("The folder to write the results.")
    protected String outputfolder; // Default in validate
    /**
     * Whether to submit drop statements prior to running.
     */
    @Parameter(value="Whether to submit drop statements prior to running.", valueAsSwitch = "true")
    protected boolean dropfirst = false;
    private MutantTableMap mutantTables = new MutantTableMap();
    SQLWriter sqlWriter;

    @Override
    public void task() {
        // Setup
        if (inputfolder == null) {
            inputfolder = locationsConfiguration.getResultsDir() + File.separator + "generatedresults" + File.separator;
        }
        if (outputfolder == null) {
            outputfolder = locationsConfiguration.getResultsDir() + File.separator;
        }
        
        // Start results file
        CSVResult result = new CSVResult();
        result.addValue("technique", this.getClass().getName());
        result.addValue("dbms", databaseConfiguration.getDbms());
        result.addValue("casestudy", casestudy);
        result.addValue("trial", trial);

        // Instantiate the DBMS and related objects
        DBMS dbms;
        try {
            dbms = DBMSFactory.instantiate(databaseConfiguration.getDbms());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }
        sqlWriter = dbms.getSQLWriter();
        DatabaseInteractor databaseInteractor = dbms.getDatabaseInteractor(casestudy, databaseConfiguration, locationsConfiguration);

        // Get the required schema class
        Schema schema;
        try {
            schema = (Schema) Class.forName(casestudy).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }

        // Load the SQLExecutionReport for the non-mutated schema
        String reportPath = inputfolder + casestudy + ".xml";
        SQLExecutionReport originalReport = XMLSerialiser.load(reportPath);

        // Start mutation timing
        long startTime = System.currentTimeMillis();

        // Create the mutant schemas
        ConstraintMutatorWithoutFK cm = new ConstraintMutatorWithoutFK();
        List<Schema> mutants = cm.produceMutants(schema);
        List<String> mutantCreateStatements = new ArrayList<>();
        List<String> mutantDropStatements = new ArrayList<>();
        // smart step- minimise the create/drops for each mutant
        int i = 1;
        for (Schema mutant : mutants) {
            mutantCreateStatements.add(writeCreateStatement(mutant, i));
            mutantDropStatements.add(writeDropStatement(mutant, i));
            addToMutantTableMap(mutant, i);
            i++;
        }

        // Drop tables
        List<String> dropStmts = sqlWriter.writeDropTableStatements(schema, true);
        if (dropfirst) {
            for (String drop : dropStmts) {
                databaseInteractor.executeUpdate(drop);
            }
        }

        // Create original schema tables
        for (String create : sqlWriter.writeCreateTableStatements(schema)) {
            databaseInteractor.executeUpdate(create);
        }
        // Create mutant schema tables
        for (String create : mutantCreateStatements) {
            databaseInteractor.executeUpdate(create);
        }
        
        HashSet<String> killed = new HashSet<>();
        
        // get the original mutant reports
        List<SQLInsertRecord> insertStmts = originalReport.getInsertStatements();
        for (SQLInsertRecord insertRecord : insertStmts) {

            String insert = insertRecord.getStatement();
            String affectedTable = getAffectedTable(insert);
            int returnCode = insertRecord.getReturnCode();
            databaseInteractor.executeUpdate(insert);

            // for each applicable mutant
            for (String mutantTable : mutantTables.getMutants(affectedTable)) {
                String mutantInsert = rewriteInsert(insert, affectedTable, mutantTable);
                int mutantReturnCode = databaseInteractor.executeUpdate(mutantInsert);
                if (returnCode != mutantReturnCode) {
                    killed.add(getMutantNumber(mutantTable));
                }
            }
        }
        
        // drop mutant schema tables
        for (String drop : mutantDropStatements) {
            databaseInteractor.executeUpdate(drop);
        }
        // drop original schema tables
        for (String drop : dropStmts) {
            databaseInteractor.executeUpdate(drop);
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        result.addValue("mutationtime", totalTime);
        result.addValue("mutationscore_numerator", killed.size());
        result.addValue("mutationscore_denominator", mutants.size());

        new CSVWriter(outputfolder + casestudy + ".dat").write(result);
    }

    @Override
    protected void validateParameters() {
        //TODO: Validate parameters
    }

    public static void main(String[] args) {
        new MinimalSchemata().run(args);
    }

    /**
     * Writes the drop table statement for a mutant schema. Includes only the
     * changed table.
     *
     * @param mutant The mutant schema
     * @param id The mutant id
     * @return The create table statement
     */
    private String writeDropStatement(Schema mutant, int id) {
        String changedTable = getChangedTable(mutant);
        List<String> dropTableStatements = sqlWriter.writeDropTableStatements(mutant, true);
        for (String statement : dropTableStatements) {
            if (statement.startsWith("DROP TABLE IF EXISTS " + changedTable)) {
                return statement.replace("DROP TABLE IF EXISTS " + changedTable, "DROP TABLE IF EXISTS mutant_" + id + "_" + changedTable);
            }
        }
        throw new RuntimeException("Could not find drop table statement for mutant (" + mutant.getName() + ", table '" + changedTable + "')");
    }

    /**
     * Writes the create table statement for a mutant schema. Includes only the
     * changed table.
     *
     * @param mutant The mutant schema
     * @param id The mutant id
     * @return The create table statement
     */
    private String writeCreateStatement(Schema mutant, int id) {
        String changedTable = getChangedTable(mutant);
        List<String> dropTableStatements = sqlWriter.writeCreateTableStatements(mutant);
        for (String statement : dropTableStatements) {
            if (statement.startsWith("CREATE TABLE " + changedTable)) {
                return statement.replace("CREATE TABLE " + changedTable, "CREATE TABLE mutant_" + id + "_" + changedTable);
            }
        }
        throw new RuntimeException("Could not find create table statement for mutant (" + mutant.getName() + ", table '" + changedTable + "')");
    }

    /**
     * Retrieves the name of the changed table in a schema mutant.
     *
     * @param mutant The mutant schema
     * @return The name of the table
     */
    private static String getChangedTable(Schema mutant) {
        for (String comment : mutant.getComments()) {
            if (comment.startsWith("table=")) {
                return comment.replace("table=", "");
            }
        }
        throw new RuntimeException("Could not find changed table for mutant (" + mutant.getName() + ")");
    }

    /**
     * Retrieves the name of the table affected by an update statement.
     *
     * @param statement The update statement
     * @return The table name
     */
    private static String getAffectedTable(String statement) {
        return statement.substring("INSERT INTO ".length(), statement.indexOf('('));
    }

    /**
     * Rewrites an insert statement to redirect to a mutant table.
     *
     * @param statement The insert statement
     * @param table The original table name
     * @param mutantTable The mutant table name
     * @return The rewritten insert
     */
    private static String rewriteInsert(String statement, String table, String mutantTable) {
        return statement.replace("INSERT INTO " + table, "INSERT INTO " + mutantTable);
    }

    /**
     * Adds a mutant to the mutant tables map.
     *
     * @param mutant The mutant schema
     * @param id The mutant id
     */
    private void addToMutantTableMap(Schema mutant, int id) {
        String changedTable = getChangedTable(mutant);
        mutantTables.addMutant(changedTable, "mutant_" + id + "_" + changedTable);
    }

    /**
     * Retrieves the mutant id number from a table name.
     *
     * @param mutantTable
     * @return The mutant id
     */
    private static String getMutantNumber(String mutantTable) {
        return mutantTable.split("_")[1];
    }

    private class MutantTableMap {

        private HashMap<String, Set<String>> map;

        public MutantTableMap() {
            map = new HashMap<>();
        }

        public void addMutant(String table, String mutantTable) {
            Set<String> set;
            if (map.containsKey(table)) {
                set = map.get(table);
            } else {
                set = new LinkedHashSet<>();
                map.put(table, set);
            }
            set.add(mutantTable);
        }

        public Set<String> getMutants(String table) {
            if (map.containsKey(table)) {
                return map.get(table);
            } else {
                return new HashSet<>();
            }
        }
    }
}
