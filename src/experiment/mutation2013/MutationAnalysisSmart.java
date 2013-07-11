/*
 */
package experiment.mutation2013;

import experiment.ExperimentConfiguration;
import experiment.ExperimentalResults;
import experiment.util.XMLSerialiser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.schemaanalyst.configuration.Configuration;
import org.schemaanalyst.configuration.FolderConfiguration;
import org.schemaanalyst.dbms.DBMS;
import org.schemaanalyst.dbms.DatabaseInteractor;
import org.schemaanalyst.mutation.MutationReport;
import org.schemaanalyst.mutation.MutationUtilities;
import org.schemaanalyst.mutation.SQLExecutionReport;
import org.schemaanalyst.mutation.SQLInsertRecord;
import org.schemaanalyst.sqlrepresentation.Schema;
import org.schemaanalyst.sqlwriter.SQLWriter;

import plume.Options;

/**
 *
 * @author chris
 */
public class MutationAnalysisSmart {

    private static int STILL_BORN = -1;
    private static SQLWriter sqlWriter;
    private static MutantTableMap mutantTables = new MutantTableMap();

    public static void main(String[] args) {
        
        int inserts = 0;
        int totalInserts = 0;
        
        // parse options
        Options options = new Options("MutationAnalysis [options]", new Configuration());
        options.parse_or_usage(args);
        ExperimentConfiguration.project = Configuration.project;

        // Start results file
        ExperimentalResults experimentalResults = ExperimentalResults.retrieve();
        experimentalResults.reset();
        experimentalResults.addResult("datagenerator", Configuration.datagenerator);
        experimentalResults.addResult("database", MutationUtilities.removePrefixFromCaseStudyName(Configuration.database));
        experimentalResults.addResult("type", MutationUtilities.removePrefixFromCaseStudyName(Configuration.type));
        experimentalResults.addResult("trial", Integer.toString(Configuration.trial));

        // create the database using reflection; this is based on the
        // type of the database provided in the configuration (i.e.,
        // the user could request the Postres database in FQN)
        DBMS database = null;
        try {
            database = (DBMS) Class.forName(Configuration.type).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Could not construct database type \"" + Configuration.type + "\"");
        }
        sqlWriter = database.getSQLWriter();

        // load the SQL execution report for the ORIGINAL create tables
        String reportPath = FolderConfiguration.results_dir + File.separator + "data-generation" + File.separator + Configuration.database + ".xml";
        SQLExecutionReport originalReport = XMLSerialiser.load(reportPath);

        // initialize the connection to the real relational database
        DatabaseInteractor databaseInteraction = database.getDatabaseInteractor();

        // create the schema using reflection; this is based on the
        // name of the database provided in the configuration
        Schema schema = null;
        try {
            schema = (Schema) Class.forName(Configuration.database).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Could not construct schema \"" + Configuration.database + "\"");
        }

        // start mutation timing
        long startTime = System.currentTimeMillis();

        boolean stillBorn = false;

        MutationReport mutationReport = new MutationReport();
        mutationReport.setOriginalReport(originalReport);

        // create all of the MUTANT SCHEMAS that we store inside of the database 
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
        
        // create original schema tables
        for (String create: sqlWriter.writeCreateTableStatements(schema)) {
            databaseInteraction.executeUpdate(create);
        }
        // create mutant schema tables
        for (String create: mutantCreateStatements) {
            databaseInteraction.executeUpdate(create);
        }
        
        HashSet<String> killed = new HashSet<>();
        
        // get the original mutant reports
        SQLExecutionReport retrievedOriginalReport = originalReport;
        List<SQLInsertRecord> originalInsertStatements = retrievedOriginalReport.getInsertStatements();
        for (SQLInsertRecord originalInsertRecord : originalInsertStatements) {
            
            totalInserts++;
            
            String insert = originalInsertRecord.getStatement();
            String affectedTable = getAffectedTable(insert);
            int returnCode = originalInsertRecord.getReturnCode();
            databaseInteraction.executeUpdate(insert);
            inserts++;
            
            // for each applicable mutant
            for (String mutantTable: mutantTables.getMutants(affectedTable)) {
                String mutantInsert = rewriteInsert(insert, affectedTable, mutantTable);
                int mutantReturnCode = databaseInteraction.executeUpdate(mutantInsert);
                inserts++;
                if (returnCode != mutantReturnCode) {
                    killed.add(getMutantNumber(mutantTable));
                }
            }
        }
        
        // drop mutant schema tables
        for (String drop : mutantDropStatements) {
            databaseInteraction.executeUpdate(drop);
        }
        // drop original schema tables
        for (String drop : sqlWriter.writeDropTableStatements(schema, true)) {
            databaseInteraction.executeUpdate(drop);
        }
        
        // END TIME FOR mutation analysis
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        
        experimentalResults.addResult("mutationtime", new Long(totalTime).toString());
        experimentalResults.addResult("mutationscore_numerator", Integer.toString(killed.size()));
        experimentalResults.addResult("mutationscore_denominator", Integer.toString(mutants.size()));
        
        if (!experimentalResults.wroteHeader()) {
            experimentalResults.writeHeader();
            experimentalResults.didWriteCountHeader();
        }
        
        experimentalResults.writeResults();
        experimentalResults.save();
        
        System.out.println("INSERTS: "+inserts);
        System.out.println("TOTAL INSERTS: "+totalInserts);
    }

    /**
     * Writes the drop table statement for a mutant schema. Includes only the
     * changed table.
     *
     * @param mutant The mutant schema
     * @param id The mutant id
     * @return The create table statement
     */
    private static String writeDropStatement(Schema mutant, int id) {
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
    private static String writeCreateStatement(Schema mutant, int id) {
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
     * @param statement The update statement
     * @return The table name
     */
    private static String getAffectedTable(String statement) {
        return statement.substring("INSERT INTO ".length(), statement.indexOf('('));
    }
    
    /**
     * Rewrites an insert statement to redirect to a mutant table.
     * @param statement The insert statement
     * @param table The original table name
     * @param mutantTable The mutant table name
     * @return The rewritten insert
     */
    private static String rewriteInsert(String statement, String table, String mutantTable) {
        return statement.replace("INSERT INTO "+table, "INSERT INTO "+mutantTable);
    }

    /**
     * Adds a mutant to the mutant tables map.
     * @param mutant The mutant schema
     * @param id The mutant id
     */
    private static void addToMutantTableMap(Schema mutant, int id) {
        String changedTable = getChangedTable(mutant);
        mutantTables.addMutant(changedTable, "mutant_"+id+"_"+changedTable);
    }
    
    /**
     * Retrieves the mutant id number from a table name.
     * @param mutantTable
     * @return The mutant id
     */
    private static String getMutantNumber(String mutantTable) {
        return mutantTable.split("_")[1];
    }
}
