/*
 */
package org.schemaanalyst.mutation.analysis.technique;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.time.StopWatch;
import org.schemaanalyst.configuration.ExperimentConfiguration;

import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DBMSFactory;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.Mutant;
import org.schemaanalyst.util.runner.Runner;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;
import org.schemaanalyst.util.csv.CSVResult;
import org.schemaanalyst.util.csv.CSVFileWriter;
import org.schemaanalyst.util.runner.Description;
import org.schemaanalyst.util.runner.Parameter;
import org.schemaanalyst.util.runner.RequiredParameters;
import org.schemaanalyst.util.xml.XMLSerialiser;

import org.schemaanalyst.mutation.analysis.result.SQLExecutionReport;
import org.schemaanalyst.mutation.analysis.result.SQLInsertRecord;
import org.schemaanalyst.mutation.equivalence.ChangedTableFinder;
import org.schemaanalyst.mutation.pipeline.MutantRemover;
import org.schemaanalyst.mutation.pipeline.MutationPipeline;
import org.schemaanalyst.mutation.pipeline.MutationPipelineFactory;
import org.schemaanalyst.sqlrepresentation.Table;
import org.schemaanalyst.sqlrepresentation.constraint.Constraint;
import org.schemaanalyst.util.csv.CSVDatabaseWriter;

/**
 * <p> {@link Runner} for the 'Minimal Schemata' style of mutation analysis.
 * This requires that the result generation tool has been run, as it bases the
 * mutation analysis on the results produced by it.
 * </p>
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
    @Parameter(value = "Whether to submit drop statements prior to running.", valueAsSwitch = "true")
    protected boolean dropfirst = false;
    /**
     * The mutation pipeline to use to generate mutants.
     */
    @Parameter(value = "The mutation pipeline to use to generate mutants.",
            choicesMethod = "org.schemaanalyst.mutation.pipeline.MutationPipelineFactory.getPipelineChoices")
    protected String mutationPipeline = "ICST2013";
    /**
     * Whether to write the results to a CSV file.
     */
    @Parameter(value = "Whether to write the results to a CSV file.")
    protected boolean resultsToFile = true;
    /**
     * Whether to write the results to a database.
     */
    @Parameter(value = "Whether to write the results to a database.")
    protected boolean resultsToDatabase = false;
    private MutantTableMap mutantTables = new MutantTableMap();
    private SQLWriter sqlWriter;
    private Schema schema;

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
        result.addValue("technique", this.getClass().getSimpleName());
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
        try {
            schema = (Schema) Class.forName(casestudy).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }

        // Load the SQLExecutionReport for the non-mutated schema
        String reportPath = inputfolder + casestudy + ".xml";
        SQLExecutionReport originalReport = XMLSerialiser.load(reportPath);

        // Start mutation timing
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        StopWatch mutantGenerationStopWatch = constructSuspendedStopWatch();
        StopWatch dropsStopWatch = constructSuspendedStopWatch();
        StopWatch createsStopWatch = constructSuspendedStopWatch();
        StopWatch insertsStopWatch = constructSuspendedStopWatch();

        // Create the mutant schemas
        // Get the mutation pipeline and generate mutants
        mutantGenerationStopWatch.resume();
        MutationPipeline<Schema> pipeline;
        try {
            pipeline = MutationPipelineFactory.<Schema>instantiate(mutationPipeline, schema);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        List<Mutant<Schema>> mutants = pipeline.mutate();
        mutantGenerationStopWatch.stop();

        // schemata step- rename constraints
        renameConstraints(mutants);

        // smart step- minimise the create/drops for each mutant
        List<String> mutantCreateStatements = new ArrayList<>();
        List<String> mutantDropStatements = new ArrayList<>();
        int i = 0;
        for (Mutant<Schema> mutant : mutants) {
            mutantCreateStatements.add(writeCreateStatement(mutant, i));
            mutantDropStatements.add(writeDropStatement(mutant, i));
            addToMutantTableMap(mutant, i);
            i++;
        }

        // Drop tables
        dropsStopWatch.resume();
        List<String> dropStmts = sqlWriter.writeDropTableStatements(schema, true);
        if (dropfirst) {
            for (String drop : dropStmts) {
                databaseInteractor.executeUpdate(drop);
            }
        }
        dropsStopWatch.suspend();

        // Create original schema tables
        createsStopWatch.resume();
        boolean quasiSchema = false;
        for (String create : sqlWriter.writeCreateTableStatements(schema)) {
            Integer res = databaseInteractor.executeUpdate(create);
            if (res.intValue() == -1) {
                quasiSchema = true;
            }
        }
        createsStopWatch.suspend();

        // Only do mutation analysis if the schema is valid
        HashSet<String> killed = new HashSet<>();
        if (!quasiSchema) {
            // Create mutant schema tables
            createsStopWatch.resume();
            for (String create : mutantCreateStatements) {
                databaseInteractor.executeUpdate(create);
            }
            createsStopWatch.suspend();

            // get the original mutant reports
            insertsStopWatch.resume();
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
            insertsStopWatch.suspend();

            // drop mutant schema tables
            dropsStopWatch.resume();
            for (String drop : mutantDropStatements) {
                databaseInteractor.executeUpdate(drop);
            }
            // drop original schema tables
            for (String drop : dropStmts) {
                databaseInteractor.executeUpdate(drop);
            }
            dropsStopWatch.suspend();
        }

        stopWatch.stop();
        dropsStopWatch.stop();
        createsStopWatch.stop();
        insertsStopWatch.stop();

        result.addValue("mutationtime", stopWatch.getTime());
        result.addValue("mutationscore_numerator", (!quasiSchema) ? killed.size() : mutants.size());
        result.addValue("mutationscore_denominator", mutants.size());
        result.addValue("mutationpipeline", mutationPipeline);
        result.addValue("dropstime", dropsStopWatch.getTime());
        result.addValue("createstime", createsStopWatch.getTime());
        result.addValue("insertstime", insertsStopWatch.getTime());
        result.addValue("mutantgenerationtime", mutantGenerationStopWatch.getTime());

        if (resultsToFile) {
            new CSVFileWriter(outputfolder + casestudy + ".dat").write(result);
        }
        if (resultsToDatabase) {
            new CSVDatabaseWriter(databaseConfiguration, new ExperimentConfiguration()).write(result);
        }
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
    private String writeDropStatement(Mutant<Schema> mutant, int id) {
        String changedTable = getChangedTable(mutant);
        List<String> dropTableStatements = sqlWriter.writeDropTableStatements(mutant.getMutatedArtefact(), true);
        for (String statement : dropTableStatements) {
            if (statement.equals("DROP TABLE IF EXISTS " + changedTable)) {
                return statement.replace("DROP TABLE IF EXISTS " + changedTable, "DROP TABLE IF EXISTS mutant_" + id + "_" + changedTable);
            }
        }
        throw new RuntimeException("Could not find drop table statement for mutant (" + mutant.getMutatedArtefact().getName() + ", table '" + changedTable + "')");
    }

    /**
     * Writes the create table statement for a mutant schema. Includes only the
     * changed table.
     *
     * @param mutant The mutant schema
     * @param id The mutant id
     * @return The create table statement
     */
    private String writeCreateStatement(Mutant<Schema> mutant, int id) {
        String changedTable = getChangedTable(mutant);
        List<String> dropTableStatements = sqlWriter.writeCreateTableStatements(mutant.getMutatedArtefact());
        for (String statement : dropTableStatements) {
            if (statement.startsWith("CREATE TABLE " + changedTable)) {
                return statement.replace("CREATE TABLE " + changedTable, "CREATE TABLE mutant_" + id + "_" + changedTable);
            }
        }
        throw new RuntimeException("Could not find create table statement for mutant (" + mutant.getMutatedArtefact().getName() + ", table '" + changedTable + "')");
    }

    /**
     * Retrieves the name of the changed table in a schema mutant.
     *
     * @param mutant The mutant schema
     * @return The name of the table
     */
    private String getChangedTable(Mutant<Schema> mutant) {
        // Reapply removers if needed
        Schema modifiedSchema = schema.duplicate();
        List<Mutant<Schema>> list = Arrays.asList(new Mutant<>(modifiedSchema, ""));
        for (MutantRemover mutantRemover : mutant.getRemoversApplied()) {
            list = mutantRemover.removeMutants(list);
        }
        if (list.size() != 1) {
            throw new RuntimeException("Applying the MutantRemovers used for a "
                    + "mutant on the original schema did not produce only 1 "
                    + "schema (expected: 1, actual: " + list.size() + ")");
        }
        modifiedSchema = list.get(0).getMutatedArtefact();

        // Find the changed table
        Table table = ChangedTableFinder.getDifferentTable(modifiedSchema, mutant.getMutatedArtefact());
        if (table != null) {
            return table.getName();
        } else {
            throw new RuntimeException("Could not find changed table for mutant (" + mutant.getMutatedArtefact().getName() + ": " + mutant.getDescription() + ")");
        }
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
    private void addToMutantTableMap(Mutant<Schema> mutant, int id) {
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

    /**
     * Prepends each constraint in mutants with the relevant mutation number
     *
     * @param mutants
     */
    private static void renameConstraints(List<Mutant<Schema>> mutants) {
        for (int i = 0; i < mutants.size(); i++) {
            Schema mutantSchema = mutants.get(i).getMutatedArtefact();
            for (Constraint constraint : mutantSchema.getConstraints()) {
                if (constraint.hasIdentifier() && constraint.getIdentifier().get() != null) {
                    String name = constraint.getIdentifier().get();
                    constraint.setName("mutant_" + i + "_" + name);
                }
            }
        }
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
    
    private StopWatch constructSuspendedStopWatch() {
        StopWatch dropsStopwatch = new StopWatch();
        dropsStopwatch.start();
        dropsStopwatch.suspend();
        return dropsStopwatch;
    }
}
